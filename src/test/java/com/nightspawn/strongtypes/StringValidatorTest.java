package com.nightspawn.strongtypes;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.nightspawn.strongtypes.examples.NonEmptyString;

public class StringValidatorTest extends ValidatorTestBase<String> {

	@Test
	public void testStrings() throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException {

		String[] validStrings = { "1", "22", "\ntralala" };
		testAll(validStrings, NonEmptyStringTest.class, 0, "false positive, ");

		expectedException.expect(InvocationTargetException.class);
		String[] emptyStrings = { "", null };
		testAll(emptyStrings, NonEmptyStringTest.class, 2, "false negative, ");

	}

	static class NonEmptyStringTest extends SimpleValueObject<String> {
		private static final long serialVersionUID = 1L;

		public NonEmptyStringTest(String value) {
			super(value);
			requireValid();
		}

		@NonEmptyString
		@Override
		public String getValue() {
			return value();
		}

	}
}
