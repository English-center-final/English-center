package com.trungtamtienganh.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.trungtamtienganh.utils.DateProcessor;

public class DateStartValidator implements ConstraintValidator<DateStart, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || !DateProcessor.toLocalDate(value).isAfter(LocalDate.now()))
			return false;

		return true;
	}
}
