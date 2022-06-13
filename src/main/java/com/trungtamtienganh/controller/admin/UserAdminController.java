package com.trungtamtienganh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.AccountDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.dto.UserRequestDTO;
import com.trungtamtienganh.dto.UserStatusDTO;
import com.trungtamtienganh.service.UserService;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin
public class UserAdminController {

	@Autowired
	private UserService userService;

	@GetMapping("")
	public PaginationWrapper<List<UserDTO>> getList(
			@RequestParam(name = "username", required = false, defaultValue = "") String username,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {

		return userService.getList(username, page, size);
	}

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createAccount(@Valid @RequestBody AccountDTO accountDTO) {

		userService.createAccount(accountDTO);
	}
	
	@GetMapping("/{id}")
	public UserDTO getUserProfile(@PathVariable("id") Integer id) {
		return userService.getUserById(id);
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO, @PathVariable("id") int id) {
		userRequestDTO.setId(id);
		return userService.updateUser(userRequestDTO);
	}

	@PutMapping("/{id}/admin-role")
	public void updateAdminRole(@PathVariable("id") int id) {

		userService.updateAdminRole(id);
	}

	@PutMapping("/{id}/update-roles")
	public void updateAdminRole(@PathVariable("id") int id, @RequestBody List<String> roles) {

		userService.updateRoles(id, roles);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") int id) {

		userService.delete(id);
	}

	@PostMapping(value = "/new-user", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO addNewUser(@Valid @RequestBody UserDTO userDTO) {

		return userService.addNewUser(userDTO);
	}

	@PutMapping(value = "/status", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO updateStatus(@Valid @RequestBody UserStatusDTO userStatusDTO) {

		return userService.updateStatus(userStatusDTO);
	}

}
