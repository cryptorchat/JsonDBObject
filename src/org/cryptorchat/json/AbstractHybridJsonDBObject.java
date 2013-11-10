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
package org.cryptorchat.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.cryptorchat.json.helper.JsonStringImpl;
import org.cryptorchat.json.helper.ParserHelper;

import com.mongodb.DBObject;

/**
 * AbstractHybridJsonDBObject
 * Hybrid abstract class to use the benefits of these interfaces: DBObject, JsonObject.
 */
abstract public class AbstractHybridJsonDBObject extends LinkedHashMap<String,JsonValue> implements DBObject, JsonObject {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8995538847385902573L;

	/**
	 * _isPartialObject
	 */
	private transient boolean partialObject;

	protected transient ObjectId objectId;

	/**
	 * Constructor without argument.
	 */
	public AbstractHybridJsonDBObject() {
		super();
	}

	/**
	 * This constructor initialize the object from a map.
	 * @param map map to convert
	 */
	public AbstractHybridJsonDBObject(final Map<String, JsonValue> map) {
		super(map);
	}

    /**
     * Checks if this object contains a field with the given name.
     * @param field Field name for which to check
     * @return True if the field is present
     */
	@Override
	public boolean containsField(final String field) {
		return super.containsKey(field);
	}

    /**
     * Deprecated
     * @param key
     * @return True if the key is present
     * @deprecated
     */
	@Deprecated
	@Override
	public boolean containsKey(final String key) {
		return containsField(key);
	}

	/**
	 * Copy all fields from the map into the instance.
	 * @param map input map
	 */
	public abstract <K,V> void copy(final Map<K,V> map);

    /**
     * Gets a field from this object by a given name.
     * @param key The name of the field fetch
     * @return The field, if found
     */
	@Override
	public JsonValue get(final String key) {
		return super.get(key);
	}

    /**
     * Returns the value of a field as a boolean.
     * @param key the field to look up
     * @return the value of the field, or false if field does not exist
     */
	@Override
	public boolean getBoolean(final String key) {
		return getBoolean(key, false);
	}

    /**
     * Returns the value of a field as a boolean
     * @param key the field to look up
     * @param defaultValue the default value in case the field is not found
     * @return the value of the field, converted to a string
     */
	@Override
	public boolean getBoolean(final String key, final boolean defaultValue) {
		final Object foo = get( key );
		if ( foo == null ) {
			return defaultValue;
		}
		if ( foo instanceof JsonValue ) {
			final JsonValue value = (JsonValue)foo;
			if(value.getValueType() == ValueType.TRUE) {
				return true;
			}
			if(value.getValueType() == ValueType.FALSE) {
				return false;
			}
		}
		if ( foo instanceof Number ) {
			return ((Number)foo).intValue() > 0;
		}
		if ( foo instanceof Boolean ) {
			return ((Boolean)foo).booleanValue();
		}
		throw new IllegalArgumentException( "can't coerce to bool:" + foo.getClass() );
	}

    /**
     * Returns the value of a field as an <code>int</code>.
     * @param key the field to look for
     * @return the field value (or default)
     */
	@Override
	public int getInt(final String key) {
		if(get(key) instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber)get(key);
			return number.intValue();
		}
		return 0;
	}

    /**
     * Returns the value of a field as an <code>int</code>.
     * @param key the field to look for
     * @param defaultValue the default to return
     * @return the field value (or default)
     */
	@Override
	public int getInt(final String key, final int defaultValue) {
		if(get(key) instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber)get(key);
			return number.intValue();
		}
		return defaultValue;
	}

    /**
     * Returns the value of a field as an <code>JsonArray</code>.
     * @param key the field to look for
     * @return the field value (or null)
     */
	@Override
	public JsonArray getJsonArray(final String key) {
		if(get(key) instanceof JsonArray) {
			return (JsonArray)get(key);
		}
		return null;
	}

    /**
     * Returns the value of a field as an <code>JsonArray</code>.
     * @param key the field to look for
     * @return the field value (or null)
     */
	@Override
	public JsonNumber getJsonNumber(final String key) {
		if(get(key) instanceof JsonNumber) {
			return (JsonNumber)get(key);
		}
		return null;
	}

    /**
     * Returns the value of a field as an <code>JsonDBObject</code>.
     * @param key the field to look for
     * @return the field value (or null)
     */
	@Override
	public JsonDBObject getJsonObject(final String key) {
		if(get(key) instanceof JsonDBObject) {
			return (JsonDBObject)get(key);
		}
		return null;
	}

    /**
     * Returns the value of a field as an <code>JsonString</code>.
     * @param key the field to look for
     * @return the field value (or null)
     */
	@Override
	public JsonString getJsonString(final String key) {
		if(get(key) instanceof JsonString) {
			return (JsonString)get(key);
		}
		return null;
	}

    /**
     * Returns the value of a field as a string
     * @param key the field to look up
     * @return the value of the field, converted to a string
     */
	@Override
	public String getString(final String key) {
		if(get(key) instanceof JsonString) {
			final JsonString string = (JsonString)get(key);
			return string.getString();
		}
		return null;
	}

    /**
     * Returns the value of a field as a string
     * @param key the field to look up
     * @param defaultValue the default to return
     * @return the value of the field, converted to a string
     */
	@Override
	public String getString(final String key, final String defaultValue) {
		if(get(key) instanceof JsonString) {
			final JsonString string = (JsonString)get(key);
			return string.getString();
		}
		return defaultValue;
	}

	/**
	 * @return returns ValueType.OBJECT
	 */
	@Override
	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	/**
	 * Checks is the given key is exist or not.
	 * @param key the field to look up
	 * @return returns true if the key is exist
	 */
	@Override
	public boolean isNull(final String key) {
		if(get(key) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isPartialObject() {
		return partialObject;
	}

	@Override
	public void markAsPartialObject() {
		partialObject = true;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

    /** 
     * Add a key/value pair to this object
     * @param key the field name
     * @param value the field value
     * @return the <code>val</code> parameter
     */
	@Override
	public Object put(final String key, final Object value) {
		if(value instanceof JsonValue) {
			return super.put(key, (JsonValue)value);
		}
		if(value instanceof BSONObject) {
			final JsonDBObject inner = new JsonDBObject((BSONObject)value);
			return put(key, inner);
		}
		if(value instanceof ObjectId) {
			objectId = (ObjectId)value; 
			final JsonString str = new JsonStringImpl(objectId.toString());
			return super.put(key, str);
		}
		final JsonValue json = ParserHelper.parseToJsonValue(value);
		return super.put(key, json);
	}

    /**
     * Put all value from the BSONObject to the instance
     * @param object the BSONObject
     */
	@Override
	public void putAll(final BSONObject object) {
		for ( final String key : object.keySet() ) {
			final Object value = object.get(key);
			put( key , value );
		}
	}

    /**
     * Add a map to this instance.
     * Use the copy method instead!
     * @param map the map to add
     */
	@Deprecated
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putAll(final Map map) {
		for ( final Map.Entry entry : (Set<Map.Entry>)map.entrySet() ) {
			put( entry.getKey().toString() , entry.getValue() );
		}
	}

    /**
     * Deletes a field from this object.
     * @param key the field name to remove
     * @return the object removed
     */
	@Override
	public Object removeField(final String key) {
		return super.remove(key);
	}
}
