package com.trungtamtienganh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import com.trungtamtienganh.entity.ClassStatus;

public class ClassStatusValidator implements ConstraintValidator<com.trungtamtienganh.validator.ClassStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || !EnumUtils.isValidEnum(ClassStatus.class, value))
			return false;

		return true;
	}
}
