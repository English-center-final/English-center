package com.trungtamtienganh.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuntimeCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object error;

	public RuntimeCustomException() {
		super();
	}

	public RuntimeCustomException(Object error) {
		super();
		this.error = error;
	}
}
