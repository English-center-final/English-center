package com.trungtamtienganh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.repository.UserRepository;
import com.trungtamtienganh.utils.AuthenInfo;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, String> {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null)
			return false;

		AuthenInfo.checkLogin();
		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username).get();

		return passwordEncoder.matches(value, user.getPassword());
	}

}
