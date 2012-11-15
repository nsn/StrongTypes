package com.nightspawn.strongtypes;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.annotate.JsonValue;

/**
 * abstract base class for simple value objects. Wraps a simple type, Provides
 * basic implementations of Comparable and Serializable interfaces. Value
 * Objects should be immutable, thus the wrapped instance is declared final. All
 * concrete implementations should perform necessary checks in their
 * constructors, using bean validation wherever possible
 * 
 * @author nsn
 * 
 * @param <WRAPPED>
 *            wrapped type
 */
@XmlTransient
public abstract class SimpleValueObject<WRAPPED extends Comparable<WRAPPED>>
		implements Serializable, Comparable<SimpleValueObject<WRAPPED>> {
	private static final long serialVersionUID = 0L;
	private final static Validator VALIDATOR = Validation
			.buildDefaultValidatorFactory().getValidator();
	private final WRAPPED value;

	public SimpleValueObject(final WRAPPED value) {
		this.value = value;
	}

	protected WRAPPED value() {
		return value;
	}

	/**
	 * Any implementations of this method should return either instances of
	 * final classes or copies of the wrapped type instance in order to maintain
	 * immutability. Also this method can be used for JSR-303 bean validation if
	 * annotated with the desired constraints
	 * 
	 * @return a instance of the wrapped type in a way that any changes to that
	 *         instance don't affect the {@link SimpleValueObject} itself
	 */
	@XmlValue
	@JsonValue
	@NotNull
	public abstract WRAPPED getValue();

	/**
	 * determines whether the instance validates any anotated JSR-303
	 * constraints, throws an {@link IllegalArgumentException} if so. Typically
	 * called in the subclasses constructor to ensure validity
	 */
	protected void requireValid() {
		final Set<ConstraintViolation<SimpleValueObject<WRAPPED>>> constraintViolations = VALIDATOR
				.validate(this);
		if (constraintViolations.size() > 0) {
			final StringBuffer sb = new StringBuffer();
			for (final ConstraintViolation<SimpleValueObject<WRAPPED>> constraintViolation : constraintViolations) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append("[" + constraintViolation.getPropertyPath() + "] "
						+ constraintViolation.getMessage() + " {"
						+ constraintViolation.getInvalidValue() + "}");
			}
			throw new IllegalArgumentException(sb.toString());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		SimpleValueObject<?> other = SimpleValueObject.class.cast(o);
		if (value == null) {
			return other.value == null;
		}
		return value.equals(other.value);
	}

	@Override
	public int hashCode() {
		if (value == null)
			return 0;
		return value.hashCode();
	}

	@Override
	public String toString() {
		if (value == null) {
			return "null";
		} else {
			return value.toString();
		}
	}

	@Override
	public int compareTo(SimpleValueObject<WRAPPED> other) {
		if (other == null)
			return 1;
		return value.compareTo(other.value);
	}

}