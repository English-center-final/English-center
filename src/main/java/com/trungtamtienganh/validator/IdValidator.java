package com.trungtamtienganh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<Id, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if (value == null || value <= 0)
			return false;

		return true;
	}
}
