package com.trungtamtienganh.utils;

import java.util.HashMap;
import java.util.Map;

import com.trungtamtienganh.exception.MyExceptionHelper;

public class EntityValidator {

	public static void checkValidate(ValidateHandler validateHandler) {

		Map<String, String> errors = new HashMap<>();

		validateHandler.validate(errors);

		if (errors.size() > 0)
			throw MyExceptionHelper.throwEntityValidatorException(errors);

	}
}
