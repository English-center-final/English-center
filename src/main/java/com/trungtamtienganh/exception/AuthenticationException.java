package com.trungtamtienganh.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String message;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super();
		this.message = message;
	}
}
