package com.trungtamtienganh.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.AccessTokenDTO;
import com.trungtamtienganh.dto.AccountDTO;
import com.trungtamtienganh.dto.LoginRequestDTO;
import com.trungtamtienganh.dto.NameRoleOnlyDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.dto.UserRequestDTO;
import com.trungtamtienganh.dto.UserStatusDTO;

public interface UserService {

	AccessTokenDTO login(LoginRequestDTO loginRequestDTO);

	void register(UserDTO userDTO);

	void sendOtp(String username);

	AccessTokenDTO confirmOtp(LoginRequestDTO loginRequestDTO);

	AccessTokenDTO confirmPassword(LoginRequestDTO loginRequestDTO);

	UserDTO getUserById(Integer userId);

	UserDTO getUserProfile();
	
	UserDTO createAccount(AccountDTO accountDTO);
	
	UserDTO updateUser(UserRequestDTO userRequestDTO);

	String uploadImage(MultipartFile image);

	PaginationWrapper<List<UserDTO>> getList(String username, int page, int size);

	void delete(int id);

	void updateAdminRole(int id);

	void updateRoles(int id, List<String> roles);
	
	NameRoleOnlyDTO getNameRoleOnly();
	
	UserDTO addNewUser(UserDTO userDTO);
	
	UserDTO updateStatus(UserStatusDTO userStatusDTO);

}
