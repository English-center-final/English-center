package com.trungtamtienganh.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UserClassesStatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserClassesStatus {

	String message() default "Status must be NEW, CANCEL, CALLED, ACCEPT, DENY";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
