package com.trungtamtienganh.utils;

import java.util.Map;

@FunctionalInterface
public interface ValidateHandler {

	void validate(Map<String, String> errors);
}
