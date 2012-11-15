package com.nightspawn.strongtypes.examples;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NonEmptyStringValidator implements
		ConstraintValidator<NonEmptyString, String> {

	@Override
	public void initialize(NonEmptyString arg0) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !value.isEmpty();
	}

}
