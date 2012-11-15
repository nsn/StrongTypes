package com.nightspawn.strongtypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nightspawn.strongtypes.examples.ValidIP;

public class IPValidatorTest {
	private static Validator validator;
	private static String[] VALID_IPS = { "127.0.0.1", "79.110.95.2",
			"0.0.0.0", "2001:0db8:85a3:0000:0000:8a2e:0370:7334" };
	private static String[] INVALID_IPS = { "355.0.2.1", "foobar", "1.2" };

	@Rule
	public ExpectedException expexc = ExpectedException.none();

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidIPs() {
		for (String ip : VALID_IPS) {
			TestIP validIp = new TestIP(ip);
			Set<ConstraintViolation<TestIP>> constraintViolations = validator
					.validate(validIp);
			assertEquals("false negative for String: " + ip, 0,
					constraintViolations.size());
		}
	}

	@Test
	public void testInvalidIPs() {
		expexc.expect(IllegalArgumentException.class);
		for (String ip : INVALID_IPS) {
			TestIP invalidIp = new TestIP(ip);
			Set<ConstraintViolation<TestIP>> constraintViolations = validator
					.validate(invalidIp);
			assertTrue("false positive for String: " + ip,
					constraintViolations.size() > 0);
		}
	}

	class TestIP extends SimpleValueObject<String> {
		private static final long serialVersionUID = 1L;

		public TestIP(@ValidIP String value) {
			super(value);
			requireValid();
		}

		@ValidIP
		@Override
		public String getValue() {
			return value();
		}

	}
}
