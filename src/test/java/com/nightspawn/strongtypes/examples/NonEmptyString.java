package com.nightspawn.strongtypes.examples;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

/**
 * fields annotated with this type are strings that are neither null nor empty
 * 
 * @author nsn
 */
@Documented
@Constraint(validatedBy = NonEmptyStringValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
public @interface NonEmptyString {
	String message() default "{com.gameforge.modules.common.dtos.validation.NonEmpty.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
