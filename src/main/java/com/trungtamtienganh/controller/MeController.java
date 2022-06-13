package com.trungtamtienganh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.NameRoleOnlyDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserClassesSubscribeDTO;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.dto.UserExamDTO;
import com.trungtamtienganh.dto.UserRequestDTO;
import com.trungtamtienganh.service.ClassesService;
import com.trungtamtienganh.service.ExamService;
import com.trungtamtienganh.service.UserService;

@RestController
@RequestMapping("/me")
@CrossOrigin
public class MeController {

	@Value("${EMAIL}")
	String email;

	@Value("${EMAIL_PASSWORD}")
	String pass;

	@Autowired
	private UserService userService;

	@Autowired
	private ClassesService classesService;

	@Autowired
	private ExamService examService;

	@GetMapping()
	public UserDTO getUserProfile() {
		return userService.getUserProfile();
	}

	@PutMapping()
	public UserDTO updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.updateUser(userRequestDTO);
	}

	@GetMapping("/role")
	public NameRoleOnlyDTO getUserRole() {
		return userService.getNameRoleOnly();
	}

	@GetMapping("/classes")
	public List<UserClassesSubscribeDTO> getUserClasses() {
		return classesService.getUserClassesByUserId();
	}

	@PutMapping("/image")
	public String updateImage(@RequestPart("image") MultipartFile image) {

		return userService.uploadImage(image);
	}

//	@PutMapping("/password")
//	public String changePassword(@Valid @RequestBody MultipartFile image) {
//
//		return userService.uploadImage(image);
//	}

	@GetMapping("/exam-history")
	public PaginationWrapper<List<UserExamDTO>> getListUserExam(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {
		
		return examService.getListUserExam(page, size);
	}
}
