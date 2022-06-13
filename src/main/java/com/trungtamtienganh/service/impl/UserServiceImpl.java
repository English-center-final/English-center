package com.trungtamtienganh.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.config.jwt.JwtTokenProvider;
import com.trungtamtienganh.converter.UserConverter;
import com.trungtamtienganh.dto.AccessTokenDTO;
import com.trungtamtienganh.dto.AccountDTO;
import com.trungtamtienganh.dto.LoginRequestDTO;
import com.trungtamtienganh.dto.NameRoleOnlyDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.dto.UserRequestDTO;
import com.trungtamtienganh.dto.UserStatusDTO;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.entity.UserRole;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.RoleRepository;
import com.trungtamtienganh.repository.UserRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.EmailService;
import com.trungtamtienganh.service.UserService;
import com.trungtamtienganh.utils.AuthenInfo;
import com.trungtamtienganh.utils.EntityValidator;
import com.trungtamtienganh.utils.FileHelpers;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RoleConstant;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private OtpService otpService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AwsS3Service awsS3Service;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AccessTokenDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByUsername(loginRequestDTO.getUsername())
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.USER));

		if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
			throw MyExceptionHelper.throwResourceNotFoundException("Wrong password ");

		String token = jwtTokenProvider.generateToken(user.getId());
		return new AccessTokenDTO(token);
	}

	@Override
	public void register(UserDTO userDTO) {
		String username = userDTO.getUsername();

		if (userRepository.findByUsername(username).isPresent())
			throw MyExceptionHelper.throwResourceNotFoundException("User is already exist");

		userRepository.save(userConverter.toRegisterUser(userDTO));

		this.sendOtp(username);

	}

	@Override
	public void sendOtp(String username) {

		Optional<User> optional = userRepository.findByUsername(username);

		if (!optional.isPresent())
			throw MyExceptionHelper.throwResourceNotFoundException("User not found");

		Integer otp = otpService.generateOTP(username);

		String name = optional.get().getName();
		try {
			emailService.sendOtpEmail(username, name, String.valueOf(otp));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AccessTokenDTO confirmOtp(LoginRequestDTO loginRequestDTO) {
		String username = loginRequestDTO.getUsername();
		Integer otp = loginRequestDTO.getOtp();

		System.out.println(username);
		System.out.println(otp);

		Optional<User> optional = userRepository.findByUsername(username);

		if (!optional.isPresent())
			throw MyExceptionHelper.throwResourceNotFoundException("User not found");

		if (otp == null || otp <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		int serverOtp = otpService.getOtp(username);

		System.out.println(serverOtp);

		System.out.println(serverOtp == otp);

		if (String.valueOf(serverOtp).equals(String.valueOf(otp))) {

			otpService.clearOTP(username);

			User user = optional.get();

			user.setActived(true);
			userRepository.save(user);

			Integer userId = optional.get().getId();

			String token = jwtTokenProvider.generateToken(userId);

			return new AccessTokenDTO(token);
		} else {
			throw MyExceptionHelper.throwResourceNotFoundException("Invalid OTP");
		}

	}

	@Override
	public AccessTokenDTO confirmPassword(LoginRequestDTO loginRequestDTO) {

		String username = loginRequestDTO.getUsername();
		Integer otp = loginRequestDTO.getOtp();
		String password = loginRequestDTO.getPassword();

		System.out.println(username);
		System.out.println(otp);

		Optional<User> optional = userRepository.findByUsername(username);

		if (!optional.isPresent())
			throw MyExceptionHelper.throwResourceNotFoundException("User not found");

		if (otp == null || otp <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		int serverOtp = otpService.getOtp(username);

		System.out.println(serverOtp);

		System.out.println(serverOtp == otp);

		if (String.valueOf(serverOtp).equals(String.valueOf(otp))) {

			otpService.clearOTP(username);

			User user = optional.get();
			user.setPassword(passwordEncoder.encode(password));
			user.setActived(true);
			userRepository.save(user);

			Integer userId = optional.get().getId();
			String token = jwtTokenProvider.generateToken(userId);

			return new AccessTokenDTO(token);
		} else {
			throw MyExceptionHelper.throwResourceNotFoundException("Invalid OTP");
		}

	}

	@Override
	public UserDTO getUserById(Integer userId) {
		if (userId == null || userId <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();
		
		User user = userRepository.findById(userId).get();
		return userConverter.toUserDTO(user);
	}

	@Override
	public UserDTO getUserProfile() {
		AuthenInfo.checkLogin();
		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username).get();
		return userConverter.toUserDTO(user);
	}

	@Override
	public String uploadImage(MultipartFile image) {
		AuthenInfo.checkLogin();
		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username).get();

		if (!FileHelpers.checkImageExtension(image))
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (user.getImage() != null) {
			awsS3Service.deleteObjectFromUrl(user.getImage());
		}

		String imageUrl = awsS3Service.uploadObject(image);
		user.setImage(imageUrl);

		userRepository.save(user);

		return imageUrl;
	}

	@Override
	public PaginationWrapper<List<UserDTO>> getList(String username, int page, int size) {
		if (username == null || page < 0 || size <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<User> usersPage = userRepository.findAllByUsernameContaining(username, PageRequest.of(page, size));

		var usersPageResult = new PaginationWrapper<List<UserDTO>>();
		usersPageResult.setPage(page);
		usersPageResult.setSize(size);
		usersPageResult.setTotalPages(usersPage.getTotalPages());

		List<UserDTO> userDTOs = usersPage.toList().stream().map(user -> userConverter.toUserDTO(user))
				.collect(Collectors.toList());
		usersPageResult.setData(userDTOs);

		return usersPageResult;
	}

	@Override
	public void delete(int id) {
		getUser(id);
		userRepository.deleteById(id);
	}

	@Override
	public void updateAdminRole(int id) {
		User user = getUser(id);

		if (user.isAdmin())
			return;

		user.getRoles().clear();
		user.getRoles().add(new UserRole(user, roleRepository.findByName(RoleConstant.ROLE_ADMIN)));

		userRepository.save(user);
	}

	@Override
	public void updateRoles(int id, List<String> roles) {
		User user = getUser(id);

		List<UserRole> userRoles = roles.stream().filter(roleEle -> RoleConstant.checkNotUserAndAdmin(roleEle))
				.map(roleEle -> new UserRole(user, roleRepository.findByName(roleEle))).collect(Collectors.toList());

		user.getRoles().clear();
		user.getRoles().addAll(userRoles);

		userRepository.save(user);

	}

	private User getUser(int id) {

		if (id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		return userRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.USER));
	}

	@Override
	public UserDTO createAccount(AccountDTO accountDTO) {

		validate(accountDTO);

		User user = userConverter.toUser(accountDTO);
		User saveUser = userRepository.save(user);

		return userConverter.toUserDTO(saveUser);
	}

	public void validate(AccountDTO accountDTO) {

		if (accountDTO == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		String username = accountDTO.getUsername();

		if (userRepository.existsByUsername(username))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.USER);

		EntityValidator.checkValidate(errors -> {

			if (userRepository.existsByUsername(username))
				errors.put("username", "User is already exist");
		});

	}

	@Override
	public NameRoleOnlyDTO getNameRoleOnly() {

		AuthenInfo.checkLogin();

		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.USER));

		List<String> roles = user.getRoles().stream().map(userRole -> userRole.getRole().getName())
				.collect(Collectors.toList());

		return new NameRoleOnlyDTO(user.getName(), roles);
	}

	@Override
	public UserDTO updateUser(UserRequestDTO userRequestDTO) {
		
		User user = userConverter.toUser(userRequestDTO);
		userRepository.save(user);
		
		return userConverter.toUserDTO(user);
	}

	@Override
	public UserDTO addNewUser(UserDTO userDTO) {
		String username = userDTO.getUsername();

		if (userRepository.findByUsername(username).isPresent()) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("username", "User is already exist");
			throw MyExceptionHelper.throwEntityValidatorException(errors);
		}
			

		User user = userRepository.save(userConverter.toRegisterUser(userDTO));
		
		return userConverter.toUserDTO(user);
	}

	@Override
	public UserDTO updateStatus(UserStatusDTO userStatusDTO) {
		User user = userRepository.findById(userStatusDTO.getId())
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.USER));

		user.setActived(userStatusDTO.isActived());
		
		userRepository.save(user);
		
		return userConverter.toUserDTO(user);
	}

}
