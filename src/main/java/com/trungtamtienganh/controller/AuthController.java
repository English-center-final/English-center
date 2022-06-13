package com.trungtamtienganh.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.AccessTokenDTO;
import com.trungtamtienganh.dto.LoginRequestDTO;
import com.trungtamtienganh.dto.UserDTO;
import com.trungtamtienganh.service.UserService;
import com.trungtamtienganh.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Value("${EMAIL}")
	String email;

	@Value("${EMAIL_PASSWORD}")
	String pass;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailServiceImpl emailSender;

	@PostMapping("/registry")
	public void register(@Valid @RequestBody UserDTO userDTO) {

		userService.register(userDTO);
	}

	@PostMapping("/login")
	public AccessTokenDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

		return userService.login(loginRequestDTO);
	}

	@PostMapping("/confirm-account")
	public AccessTokenDTO confirmOtp(@RequestBody LoginRequestDTO loginRequestDTO) {

		return userService.confirmOtp(loginRequestDTO);
	}

	@PostMapping("/reset-otp")
	public void resetOtp(@RequestBody LoginRequestDTO loginRequestDTO) {

		userService.sendOtp(loginRequestDTO.getUsername());
	}

	@PostMapping("/confirm-password")
	public AccessTokenDTO confirmPassword(@RequestBody LoginRequestDTO loginRequestDTO) {

		return userService.confirmPassword(loginRequestDTO);
	}

	@GetMapping
	public String testSendEmail() {
		try {
			emailSender.sendOtpEmail("nhathao00852@gmail.com", "Test", "32145");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return email + ", " + pass;
	}
}
