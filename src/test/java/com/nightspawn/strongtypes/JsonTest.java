package com.nightspawn.strongtypes;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JsonTest {

	@Test
	public void testSerialization() throws JsonGenerationException,
			JsonMappingException, IOException {
		JsonTestObject to = new JsonTestObject();

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(to));
	}

	@XmlRootElement
	private class JsonTestObject {
		private JsonString stringValue;
		private JsonInt intValue;

		public JsonTestObject() {
			stringValue = new JsonString("jsonString");
			intValue = new JsonInt(123);
		}

		public JsonString getStringValue() {
			return stringValue;
		}

		public void setStringValue(JsonString stringValue) {
			this.stringValue = stringValue;
		}

		public JsonInt getIntValue() {
			return intValue;
		}

		public void setIntValue(JsonInt intValue) {
			this.intValue = intValue;
		}

	}

	private class JsonString extends SimpleValueObject<String> {

		public JsonString(String value) {
			super(value);
			// TODO Auto-generated constructor stub
		}

		@Override
		@XmlValue
		@JsonValue
		@NotNull
		public String getValue() {
			return new String(value());
		}

	}

	private class JsonInt extends SimpleValueObject<Integer> {

		public JsonInt(Integer value) {
			super(value);
		}

		@Override
		@XmlValue
		@JsonValue
		@NotNull
		public Integer getValue() {
			return new Integer(value().intValue());
		}

	}

}
