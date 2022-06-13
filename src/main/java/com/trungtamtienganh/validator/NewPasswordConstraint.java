package com.trungtamtienganh.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
@Documented
@Constraint(validatedBy = NewPasswordConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewPasswordConstraint {
	String message() default "The new password cannot be the same as the old password";
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
