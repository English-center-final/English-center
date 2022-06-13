package com.trungtamtienganh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResultValidator implements ConstraintValidator<Result, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value.equals("a") || value.equals("b") || value.equals("c") || value.equals("d"))
			return true;

		return false;
	}
}
