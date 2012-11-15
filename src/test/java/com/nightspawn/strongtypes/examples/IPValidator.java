package com.nightspawn.strongtypes.examples;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.net.InetAddresses;

public class IPValidator implements ConstraintValidator<ValidIP, String> {

	@Override
	public void initialize(ValidIP arg0) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return InetAddresses.isInetAddress(value);
	}

}
