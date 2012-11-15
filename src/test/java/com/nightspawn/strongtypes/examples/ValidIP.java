package com.nightspawn.strongtypes.examples;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * fields annotated with this type represent valid ip addresses
 * 
 * @author nsn
 */
@Documented
@Constraint(validatedBy = IPValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIP {
	String message() default "{com.gameforge.modules.common.dtos.validation.ValidIP.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
