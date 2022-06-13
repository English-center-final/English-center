package com.trungtamtienganh.dto;

import com.trungtamtienganh.validator.NewPasswordConstraint;
import com.trungtamtienganh.validator.PasswordsEqualConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDTO {

	@PasswordsEqualConstraint
	private String oldPassword;	
	
	@NewPasswordConstraint
	private String newPassword;

}