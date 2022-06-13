package com.trungtamtienganh.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.trungtamtienganh.utils.MyConstant;

public class UserClassesStatusValidator implements ConstraintValidator<UserClassesStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		List<String> listStatus = new ArrayList<String>(Arrays.asList(MyConstant.NEW, MyConstant.CALLED,
				MyConstant.CANCEL, MyConstant.ACCEPT, MyConstant.DENY));

		if (value == null || !listStatus.contains(value))
			return false;

		return true;
	}
}
