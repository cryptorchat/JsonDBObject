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

import javax.json.JsonString;

/**
 * Basic implementation of javax.json.JsonString
 */
public class JsonStringImpl implements JsonString, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4549832093027818753L;

	private final transient String string;

	/**
	 * Constructor: initialize the instance with the string.
	 * @param string the given string
	 */
	public JsonStringImpl(final String string) {
		this.string = string;
	}

	/**
	 * @return ValueType.STRING
	 */
	@Override
	public ValueType getValueType() {
		return ValueType.STRING;
	}

	/**
	 * Compares the instance to CharSequence.
	 * @return a char sequence for the instance
	 */
	@Override
	public CharSequence getChars() {
		return string;
	}

	/**
	 * Returns the String representation of the instance
	 * @return a String object
	 */
	@Override
	public String getString() {
		return string;
	}

	/**
	 * Returns the String representation of the instance
	 * @return a String object
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * Compares the specified object with the instance object for equality.
	 * @param obj the object to be compared for equality
	 * @return true if the specified object is equal to the instance
	 */
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof JsonString) {
			final JsonString jsonString = (JsonString)obj;
			return jsonString.getString().equals(string);
		}
		return false;
	}

	/**
	 * @return the hash code value for the instance.
	 */
	@Override
	public int hashCode() {
		return string.hashCode();
	}
}
