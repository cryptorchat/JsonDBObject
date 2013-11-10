/**
* Copyright 2013 CryptorChat
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.cryptorchat.json.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

/**
 * Basic implementation of javax.json.JsonNumber
 */
public class JsonNumberImpl implements JsonNumber, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1132473084247378404L;

	private final transient BigDecimal bigDecimal;

	/**
	 * Constructor: initialize the instance with the given number
	 * @param number the given number
	 */
	public JsonNumberImpl(final Number number) {
		if(number instanceof Double) {
			bigDecimal = new BigDecimal((Double)number);
		} else if(number instanceof Long) {
			bigDecimal = new BigDecimal((Long)number);
		} else {
			bigDecimal = new BigDecimal(number.intValue());
		}
	}

	/**
	 * Constructor: initialize the instance with a given BigDecimal.
	 * @param bigDecimal the given BigDecimal
	 */
	public JsonNumberImpl(final BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	/**
	 * Constructor: initialize the instance with a given BigInteger.
	 * @param bigInteger the given BigInteger
	 */
	public JsonNumberImpl(final BigInteger bigInteger) {
		bigDecimal = new BigDecimal(bigInteger);
	}

	/**
	 * @return ValueType.NUMBER
	 */
	@Override
	public ValueType getValueType() {
		return ValueType.NUMBER;
	}

	/**
	 * Returns the instance as a BigDecimal object.
	 * @return a BigDecimal representation of the instance
	 */
	@Override
	public BigDecimal bigDecimalValue() {
		return bigDecimal;
	}

	/**
	 * Returns the instance as a BigInteger object.
	 * @return a BigInteger representation of the instance
	 */
	@Override
	public BigInteger bigIntegerValue() {
		return bigDecimal.toBigInteger();
	}

	/**
	 * Returns the instance as a BigInteger object.
	 * This is a convenience method for bigDecimalValue().toBigIntegerExact().
	 * @return a BigInteger representation of the instance
	 */
	@Override
	public BigInteger bigIntegerValueExact() {
		return bigDecimal.toBigIntegerExact();
	}

	/**
	 * Returns the instance as a double.
	 * @return a double representation of the instance
	 */
	@Override
	public double doubleValue() {
		return bigDecimal.doubleValue();
	}

	/**
	 * Returns the instance as an int.
	 * @return a int representation of the instance
	 */
	@Override
	public int intValue() {
		return bigDecimal.intValue();
	}

	/**
	 * Returns the instance as an int.
	 * This is a convenience method for bigDecimalValue().intValueExact().
	 * @return a int representation of the instance
	 */
	@Override
	public int intValueExact() {
		return bigDecimal.intValueExact();
	}

	/**
	 * @return true if the instance is a integral number.
	 */
	@Override
	public boolean isIntegral() {
		if(bigDecimal.scale() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the instance as a long.
	 * @return a long representation of the instance
	 */
	@Override
	public long longValue() {
		return bigDecimal.longValue();
	}

	/**
	 * Returns the instance as a long.
	 * This is a convenience method for bigDecimalValue().longValueExact().
	 * @return a long representation of the instance
	 */
	@Override
	public long longValueExact() {
		return bigDecimal.longValueExact();
	}

	/**
	 * Returns a string representation of the instance.
	 * The representation is equivalent to BigDecimal.toString().
	 * @return a string representation of the number
	 */
	@Override
	public String toString() {
		return bigDecimal.toString();
	}

	/**
	 * Compares the specified object with the instance object for equality.
	 * @param obj the object to be compared for equality
	 * @return true if the specified object is equal to the instance
	 */
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber)obj;
			return number.bigDecimalValue().equals(bigDecimal);
		}
		return false;
	}

	/**
	 * @return the hash code value for the instance.
	 */
	@Override
	public int hashCode() {
		return bigDecimal.hashCode();
	}
}
