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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.codehaus.jackson.map.ObjectMapper;
import org.cryptorchat.json.JsonDBObject;

/**
 * JacksonParser
 */
public class ParserHelper {
	/**
	 * Parse a POJO into JsonValue
	 * @param pojo the input pojo
	 * @return <code>JsonValue</code> representative of the POJO
	 */
	public static JsonValue parseToJsonValue(final Object pojo) {
		if(pojo instanceof JsonValue) {
			return (JsonValue)pojo;
		}
		if(pojo instanceof String) {
			return new JsonStringImpl(pojo.toString());
		}
		if(pojo instanceof Boolean) {
			final boolean value = (Boolean)pojo;
			if(value) {
				return JsonValue.TRUE;
			}
			return JsonValue.FALSE;
		}
		if(pojo instanceof Number) {
			return new JsonNumberImpl((Number)pojo);
		}
		if(pojo instanceof Iterable) {
			return parseToJsonArray((Iterable<?>)pojo);
		}
		if(pojo instanceof Map) {
			return parseToJsonArray((Map<?,?>)pojo);
		}
		return parseToJsonObject(pojo);
	}

	/**
	 * Parse an iterable (List) into JsonArray
	 * @param iterable the input iterable
	 * @return JsonArray representation of the input iterable
	 */
	public static <Type> JsonArray parseToJsonArray(final Iterable<Type> iterable) {
		final JsonArrayImpl list = new JsonArrayImpl();
		final Iterator<Type> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			final Type type = iterator.next();
			final JsonValue value = parseToJsonValue(type);
			list.add(value);
		}
		return list;
	}

	/**
	 * Parse a map into JsonArray
	 * @param map the input map (the key must be a String)
	 * @return JsonArray representation of the input map
	 */
	public static <K,V> JsonArray parseToJsonArray(final Map<K,V> map) {
		final JsonArrayImpl list = new JsonArrayImpl();
		final Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<K, V> item = iterator.next();
			final JsonValue key = parseToJsonValue(item.getKey());
			if(	key instanceof JsonString) {
				final JsonString keyString = (JsonString)key;
				final JsonValue value = parseToJsonValue(item.getValue());
				list.add( new JsonDBObject(keyString.getString(), value) );
			}
		}
		return list;
	}

	/**
	 * Parse an object into JsonDBObject
	 * @param object input object
	 * @return JsonDBObject representation of the input object
	 */
	public static JsonDBObject parseToJsonObject(final Object object) {
		final String str = pojoToString(object);
		return new JsonDBObject(str);
	}

	/**
	 * Parse a pojo into String
	 * @param pojo input object
	 * @return String representation of the input object
	 */
	public static String pojoToString(final Object pojo) {
		final ByteArrayOutputStream stream	= new ByteArrayOutputStream();
		try { 
			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(stream, pojo);
		} catch(IOException e) {
			return "";
			// TODO throw a normal exception
		}
		return stream.toString();
	}

	/**
	 * Parse a JsonDBObject into String
	 * @param jsonDBObject input object
	 * @return String representation of the jsonDBObject object
	 */
	public static String jsonDBObjectToString(final JsonDBObject jsonDBObject) {
		final List<String> list = new ArrayList<String>( jsonDBObject.keySet() );
		Collections.sort(list);
		final Iterator<String> iterator = list.iterator();
		final StringBuilder builder = new StringBuilder("{");

		while(iterator.hasNext()) {
			final String key = iterator.next();
			final JsonValue value = jsonDBObject.get(key);

			if(!builder.toString().equalsIgnoreCase("{")) {
				builder.append(',');
			}
			builder.append( jsonValueToJsonString(value, key) );
		}

		builder.append('}');
		return builder.toString();
	}

	/**
	 * Parse a JsonValue into String
	 * @param jsonValue input object
	 * @param key key for the input object
	 * @return String representation of the jsonValue object
	 */
	public static String jsonValueToJsonString(final JsonValue jsonValue, final String key) {
		final StringBuilder builder = new StringBuilder();
		if(key != null) {
			builder.append('\"').append(key).append("\":");
		}
		if(jsonValue instanceof JsonString) {
			builder.append('\"').append(jsonValue.toString()).append('\"');
		} else
		if(jsonValue instanceof JsonNumber || jsonValue instanceof JsonObject) {
			builder.append(jsonValue.toString());
		} else
		if(jsonValue instanceof JsonArray) {
			builder.append(jsonArrayToString((JsonArray)jsonValue));
		} else
		if(jsonValue == JsonValue.TRUE) {
			builder.append("true");
		} else
		if(jsonValue == JsonValue.FALSE) {
			builder.append("false");
		} else
		if(jsonValue == JsonValue.NULL) {
			builder.append("null");
		}
		return builder.toString();
	}

	/**
	 * Parse a JsonArray into String
	 * @param array input array
	 * @return String representation of the array object
	 */
	public static String jsonArrayToString(final JsonArray array) {
		final StringBuilder builder = new StringBuilder("[");
		final Iterator<JsonValue> iterator = array.iterator();
		while(iterator.hasNext()) {
			final JsonValue current = iterator.next();
			if(!builder.toString().equalsIgnoreCase("[")) {
				builder.append(',');
			}
			builder.append(jsonValueToJsonString(current, null));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Parse a JsonDBObject into POJO
	 * @param object jsonValue input object
	 * @param clazz type to be parse
	 * @return filled POJO
	 */
	public static <T> T createPojofromJsonDBObject(final JsonDBObject object, final Class<T> clazz) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(object.toString(), clazz);
		} catch (IOException e) {
			return null;
			// TODO throw a normal exception
		}
	}
}
