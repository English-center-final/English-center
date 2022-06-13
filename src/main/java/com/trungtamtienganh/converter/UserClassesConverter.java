package com.trungtamtienganh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.UserClassesDTO;
import com.trungtamtienganh.dto.UserClassesRequestDTO;
import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.entity.UserClasses;
import com.trungtamtienganh.repository.ClassesRepository;
import com.trungtamtienganh.repository.UserRepository;

@Component
public class UserClassesConverter {
	
	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClassesConverter classesConverter;

	public UserClassesDTO toUserClassesDTO(UserClasses userClasses) {
		UserClassesDTO userClassesDTO = new UserClassesDTO();

		User user = userClasses.getUser();

		userClassesDTO.setClasses(classesConverter.toClassesDTO(userClasses.getClasses()));
		userClassesDTO.setStatus(userClasses.getStatus());
		userClassesDTO.setUserId(user.getId());
		userClassesDTO.setEmail(user.getUsername());
		userClassesDTO.setName(user.getName());
		userClassesDTO.setPhoneNumber(user.getPhoneNumber());

		return userClassesDTO;
	}

	public UserClasses toUserClasses(UserClassesDTO userClassesDTO) {
		UserClasses userClasses = new UserClasses();

		Classes classes = classesConverter.toClasses(userClassesDTO.getClasses());

		User user = userRepository.findById(userClassesDTO.getUserId()).orElse(new User());

		userClasses.setClasses(classes);
		userClasses.setUser(user);
		userClasses.setStatus(userClassesDTO.getStatus());

		return userClasses;
	}
	
	public UserClassesRequestDTO toUserClassesRequestDTO(UserClasses userClasses) {
		UserClassesRequestDTO userClassesRequestDTO = new UserClassesRequestDTO();

		userClassesRequestDTO.setClassesId(userClasses.getClasses().getId());
		userClassesRequestDTO.setStatus(userClasses.getStatus());
		userClassesRequestDTO.setUserId(userClasses.getUser().getId());

		return userClassesRequestDTO;
	}

	public UserClasses toUserClasses(UserClassesRequestDTO userClassesRequestDTO) {

		UserClasses userClasses = new UserClasses();

		Classes classes = classesRepository.findById(userClassesRequestDTO.getClassesId()).orElse(new Classes(0));
		User user = userRepository.findById(userClassesRequestDTO.getUserId()).orElse(new User());

		userClasses.setClasses(classes);
		userClasses.setUser(user);
		userClasses.setStatus(userClassesRequestDTO.getStatus());

		return userClasses;
	}

}
