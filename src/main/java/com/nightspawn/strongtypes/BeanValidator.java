package com.nightspawn.strongtypes;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class BeanValidator {
	Validator validator;

	public BeanValidator(Validator validator) {
		super();
		this.validator = validator;
	}

	public <T> Set<ConstraintViolation<T>> getViolations(T t) {
		Set<ConstraintViolation<T>> violations = validator.validate(t);
		return violations;
	}

	public <T> void validate(T t) throws ValidationException {
		Set<ConstraintViolation<T>> violations = getViolations(t);
		if (!valid(violations))
			throw new ValidationException(getErrorMessage(violations));
	}

	public <T> boolean isValid(T t) {
		return getViolations(t).size() == 0;
	}

	public boolean valid(Set<?> v) {
		return v.isEmpty();
	}

	public static <T> String getErrorMessage(
			Set<ConstraintViolation<T>> violations) {
		StringBuffer db = new StringBuffer();
		for (ConstraintViolation<?> v : violations) {
			db.append(v.getLeafBean());
			db.append(" property [");
			db.append(v.getPropertyPath());
			db.append("] ");
			db.append(v.getMessage());
			db.append(" ");
		}
		return db.toString();
	}

}
