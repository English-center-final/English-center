package com.trungtamtienganh.exception;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trungtamtienganh.dto.ErrorDTO;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> errors = new TreeMap<>();
		ex.getBindingResult().getFieldErrors().forEach(s -> errors.put(s.getField(), s.getDefaultMessage()));

		ErrorDTO body = ErrorDTO.builder().status(400).error(errors).build();
		return new ResponseEntity<>(body, HttpStatus.OK);

	}

	@ExceptionHandler(value = { RuntimeCustomException.class })
	@ResponseStatus(code = HttpStatus.OK)
	protected ErrorDTO handleRuntimeCustomException(RuntimeCustomException e) {

		ErrorDTO result = ErrorDTO.builder().status(400).error(e.getError()).build();

		return result;
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	protected ErrorDTO handleResourceNotFoundException(ResourceNotFoundException ex) {

		ErrorDTO result = ErrorDTO.builder().status(404).error(ex.getMessage() + " không tồn tại").build();

		return result;

	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	protected ErrorDTO handleIllegalArgumentException(IllegalArgumentException ex) {

		ErrorDTO result = ErrorDTO.builder().status(400).error("Tham số đầu vào không hợp lệ").build();

		return result;

	}

	@ExceptionHandler(value = { AuthenticationException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	protected ErrorDTO handleAuthenticationException(AuthenticationException ex) {

		ErrorDTO result = ErrorDTO.builder().status(400).error(ex.getMessage()).build();

		return result;

	}

	@ExceptionHandler(value = { EntityValidatorException.class })
	@ResponseStatus(code = HttpStatus.OK)
	protected ErrorDTO handleEntityValidatorException(EntityValidatorException ex) {

		ErrorDTO result = ErrorDTO.builder().status(400).error(ex.getErrors()).build();
		return result;
	}

	@ExceptionHandler(value = { RuntimeException.class })
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorDTO handleRuntimeException(RuntimeException e) {

		ErrorDTO result = ErrorDTO.builder().status(500).error(e.getMessage()).build();
		e.printStackTrace();
		return result;
	}

}