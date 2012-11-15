package com.nightspawn.strongtypes;

import static junit.framework.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ValidatorTestBase<WRAPPED extends Comparable<WRAPPED>> {
	static Validator validator;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public <SVO extends SimpleValueObject<WRAPPED>> void testAll(WRAPPED[] arr,
			Class<SVO> clazz, int expectedViolations, String message)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		int sum = 0;
		for (WRAPPED e : arr) {
			sum += test(e, clazz);
		}
		assertEquals(message, expectedViolations, sum);
	}

	public <SVO extends SimpleValueObject<WRAPPED>> int test(WRAPPED e,
			Class<SVO> clazz) throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Constructor<?> constr = clazz.getConstructors()[0];
		SVO svo = clazz.cast(constr.newInstance(e));
		return validator.validate(svo).size();
	}

}
