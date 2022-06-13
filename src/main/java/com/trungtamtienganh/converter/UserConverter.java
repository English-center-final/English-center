package com.trungtamtienganh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.AccountDTO;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.dto.UserRequestDTO;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.entity.UserRole;
import com.trungtamtienganh.repository.RoleRepository;
import com.trungtamtienganh.repository.UserRepository;
import com.trungtamtienganh.utils.RoleConstant;

@Component
public class UserConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	

	@Autowired
	private UserRepository userRepository;

	public UserDTO toUserDTO(User user) {

		var userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setUsername(user.getUsername());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setImage(user.getImage());
		userDTO.setGender(user.isGender());
		userDTO.setActived(user.isActived());
		userDTO.setRoles(user.getRolesString());

		return userDTO;
	}

	public User toRegisterUser(UserDTO userDTO) {

		User user = new User();
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());

		String password = passwordEncoder.encode(userDTO.getPassword());
		user.setPassword(password);

		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setGender(false);
		user.setActived(false);

		UserRole userRole = new UserRole(user, roleRepository.findByName(RoleConstant.ROLE_USER));
		user.getRoles().add(userRole);

		return user;
	}
	
	public User toUser(UserRequestDTO userRequestDTO) {

		User user = userRepository.findById(userRequestDTO.getId()).orElse(new User(userRequestDTO.getId()));
		user.setName(userRequestDTO.getName());
		user.setPhoneNumber(userRequestDTO.getPhoneNumber());
		user.setGender(userRequestDTO.isGender());

		return user;
	}

	public User toUser(AccountDTO accountDTO) {

		User user = new User();
		user.setName(accountDTO.getName());
		user.setUsername(accountDTO.getUsername());

		String password = passwordEncoder.encode(accountDTO.getPassword());
		user.setPassword(password);

		UserRole userRole = new UserRole(user, roleRepository.findByName(RoleConstant.ROLE_USER));
		user.getRoles().add(userRole);

		return user;
	}

}