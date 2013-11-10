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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Basic implementation of javax.json.JsonArray
 */
public class JsonArrayImpl extends ArrayList<JsonValue> implements JsonArray, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1332124977281040914L;

	/**
	 * @return ValueType.ARRAY;
	 */
	@Override
	public ValueType getValueType() {
		return ValueType.ARRAY;
	}

	/**
	 * Returns the boolean value at the specified position.
	 * @param index index of the JSON boolean value
	 * @return true if the specified position is JsonValue.TRUE
	 */
	@Override
	public boolean getBoolean(final int index) {
		if(get(index) == JsonValue.TRUE) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the boolean value at the specified position.
	 * @param index index of the JSON boolean value
	 * @param defaultValue the default value
	 * @return the boolean value at the specified position, or the specified
	 * default value
	 */
	@Override
	public boolean getBoolean(final int index, final boolean defaultValue) {
		if(get(index) == JsonValue.TRUE) {
			return true;
		}
		if(get(index) == JsonValue.FALSE) {
			return false;
		}
		return defaultValue;
	}

	/**
	 * Returns the int value of the JsonNumber at the specified position.
	 * @param index index of the JsonNumber value
	 * @return the int value at the specified position in this instance.
	 */
	@Override
	public int getInt(final int index) {
		if(get(index) instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber)get(index);
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Returns the int value of the JsonNumber at the specified position.
	 * @param index index of the JsonNumber value
	 * @param defaultValue the default value
	 * @return the int value at the specified position in this instance, or the
	 * specified default value
	 */
	@Override
	public int getInt(final int index, final int defaultValue) {
		if(get(index) instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber)get(index);
			return number.intValue();
		}
		return defaultValue;
	}

	/**
	 * Returns a JsonArray value at the specified position in this array. 
	 * @param index index of the array
	 * @return the array at the specified position in this instance.
	 */
	@Override
	public JsonArray getJsonArray(final int index) {
		if(get(index) instanceof JsonArray) {
			return (JsonArray)get(index);
		}
		return null;
	}

	/**
	 * Returns a JsonNumber value at the specified position in this array.
	 * @param index index of the value to be returned
	 * @return the value at the specified position in this instance (or null if
	 * not found)
	 */
	@Override
	public JsonNumber getJsonNumber(final int index) {
		if(get(index) instanceof JsonNumber) {
			return (JsonNumber)get(index);
		}
		return null;
	}

	/**
	 * Returns a JsonObject value at the specified position in this array.
	 * @param index index of the value to be returned
	 * @return the value at the specified position in this instance (or null if
	 * not found)
	 */
	@Override
	public JsonObject getJsonObject(final int index) {
		if(get(index) instanceof JsonObject) {
			return (JsonObject)get(index);
		}
		return null;
	}

	/**
	 * Returns a JsonString value at the specified position in this array.
	 * @param index index of the value to be returned
	 * @return the value at the specified position in this instance (or null if
	 * not found)
	 */
	@Override
	public JsonString getJsonString(final int index) {
		if(get(index) instanceof JsonString) {
			return (JsonString)get(index);
		}
		return null;
	}

	/**
	 * Returns a String value at the specified position in this array.
	 * @param index index of the value to be returned
	 * @return the value at the specified position in this instance (or null if
	 * not found)
	 */
	@Override
	public String getString(final int index) {
		if(get(index) instanceof JsonString) {
			final JsonString string = (JsonString)get(index);
			return string.getString();
		}
		return null;
	}

	/**
	 * Returns a String value at the specified position in this array.
	 * @param index index of the value to be returned
	 * @param defaultValue the default value
	 * @return the value at the specified position in this instance, or the
	 * specified default value
	 */
	@Override
	public String getString(final int index, final String defaultValue) {
		final String str = getString(index);
		if(str == null) {
			return defaultValue;
		}
		return str;
	}

	/**
	 * Returns a list a view of the specified type for the array.
	 * @param clazz a JsonValue type
	 * @return a list view of the specified type
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Type extends JsonValue> List<Type> getValuesAs(final Class<Type> clazz) {
		final List<Type> list = new ArrayList<>();
		final Iterator<JsonValue> iterator = iterator();
		while(iterator.hasNext()) {
			final JsonValue item = iterator.next();
			final Type tmp = null;
			if(isSameType(tmp, item)) {
				list.add((Type)item);
			}
		}
		return list;
	}

	/**
	 * Decide that is the two parameter has the same class.
	 * @param object1
	 * @param object2
	 * @return true if both object has a same Class
	 */
	private boolean isSameType(final JsonValue object1, final JsonValue object2) {
		if(object1 instanceof JsonArray && object2 instanceof JsonArray) {
			return true;
		}
		if(object1 instanceof JsonNumber && object2 instanceof JsonNumber) {
			return true;
		}
		if(object1 instanceof JsonObject && object2 instanceof JsonObject) {
			return true;
		}
		if(object1 instanceof JsonString && object2 instanceof JsonString) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the value at the specified location in this array is
	 * JsonValue.NULL.
	 * @param index index of the JSON null value
	 * @return return true if the value at the specified location is
	 * JsonValue.NULL, otherwise false
	 */
	@Override
	public boolean isNull(final int index) {
		if(get(index) == JsonValue.NULL) {
			return true;
		}
		return false;
	}
}
