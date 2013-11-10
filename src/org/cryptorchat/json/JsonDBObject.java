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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.cryptorchat.json.helper.JsonArrayImpl;
import org.cryptorchat.json.helper.ParserHelper;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * JsonDBObject
 * Implementation of the AbstractHybridJsonDBObject
 */
public class JsonDBObject extends AbstractHybridJsonDBObject implements Cloneable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7536761991418331837L;

	//-------------------------------------------------------------------------
	//                             Constructors 
	//-------------------------------------------------------------------------
	/**
	 * Constructor without argument.
	 */
	public JsonDBObject() {
		super();
	}

	/**
	 * Constructor with string argument
	 * This constructor initialize the object by the given json string.
	 * @param jsonString input json string.
	 */
	public JsonDBObject(final String jsonString) {
		super();
		final InputStream stream	= new ByteArrayInputStream(jsonString.getBytes());
		final JsonReader jsonReader	= Json.createReader(stream);
		final JsonObject jsonObject	= jsonReader.readObject();
		copy(jsonObject);
	}

	/**
	 * Constructor with key-value pair
	 * This constructor initialize the object by the given key-value pair.
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 */
	public JsonDBObject(final String key, final JsonValue value) {
		super();
		put(key, value);
	}

	/**
	 * This constructor initialize the object from a DBObject.
	 * @param dbObject DBObject to convert
	 */
	public JsonDBObject(final DBObject dbObject) {
		this(dbObject.toString());
	}

	/**
	 * This constructor fetch all values from a DBCursor and put the values to
	 * an array.
	 * @param name name of the new array
	 * @param cursor the source of the data
	 * TODO test for this!!
	 */
	public JsonDBObject(final String name, final DBCursor cursor) {
		super();
		try {
			final JsonArray array = new JsonArrayImpl();
			while(cursor.hasNext()) {
				final JsonValue value = ParserHelper.parseToJsonValue( cursor.next() );
				array.add(value);
			}
			put(name, array);
		} finally {
			cursor.close();
		}
	}

	/**
	 * Copy constructor (creates a copy from an object).
	 * @param object input object
	 */
	public JsonDBObject(final JsonDBObject object) {
		super();
		copy(object);
	}

	/**
	 * This constructor parse a pojo and fill the object.
	 * @param pojo the input pojo
	 */
	public JsonDBObject(final Object pojo) {
		super();
		if(pojo instanceof JsonDBObject) {
			copy((JsonDBObject) pojo);
		} else {
			final JsonValue json = ParserHelper.parseToJsonValue( pojo );
			if(json instanceof JsonDBObject) {
				copy((JsonDBObject) json);
			}
		}
	}

	/**
	 * Constructor with key-object pair
	 * This constructor initialize the object by the given key-object pair.
	 * @param key key with which the specified value is to be associated
	 * @param object object to be associated with the specified key
	 */
	public JsonDBObject(final String key, final Object object) {
		super();
		put(key, object);
	}

	/**
	 * Constructor with collection and objectId
	 * Fetch an object from a collection
	 * @param collection the input collection
	 * @param objectId the object id to fetch
	 */
	public JsonDBObject(final DBCollection collection, final ObjectId objectId) {
		super();
		final DBObject dbo		= collection.findOne(objectId);
		final JsonDBObject tmp	= new JsonDBObject(dbo);
		copy(tmp);
	}

	/**
	 * Constructor with BSONObject
	 * Initialize the instance from the given BSONObject.
	 * @param object the input object
	 */
	public JsonDBObject(final BSONObject object) {
		super();
		putAll(object);
	}

	//-------------------------------------------------------------------------
	//                                Functions
	//-------------------------------------------------------------------------
    /**
     * Add a key/value pair to this object
     * @param key the field name
     * @param val the field value
     * @return <code>this</code>
     */
	public JsonDBObject append(final String key, final Object val){
		put(key, val);
		return this;
	}

	/**
	 * Copy all fields into a new instance and returns it.
	 * @return newly created clone object
	 */
	@Override
	public JsonDBObject clone() {
		return new JsonDBObject(this);
	}

	/**
	 * Copy all fields from the object into the instance.
	 * @param object input object
	 */
	public void copy(final JsonDBObject object) {
		copy(object.toMap());
	}

	/**
	 * Copy all fields from the map into the instance.
	 * @param map input map
	 */
	public <K,V> void copy(final Map<K,V> map) {
		final Iterator<Entry<K,V>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<K,V> item = iterator.next();
			if(item.getKey() instanceof String) {
				final String key = (String)item.getKey();
				put(key, item.getValue());
			}
		}
	}

	/**
	 * This function serialize the instance into the given class and fill it.
	 * @param clazz the output type
	 * @return created and filled POJO
	 */
	public <T> T getPOJO(final Class<T> clazz) {
		final JsonDBObject clone = this.clone();
		clone.remove("_id");
		return ParserHelper.createPojofromJsonDBObject(clone, clazz);
	}

	public void insertToCollection(final DBCollection collection) {
		final JsonDBObject[] array	= {this};
		final Object oid			= get("_id");
		if(oid != null && !ObjectId.isValid(oid.toString())) {
			objectId = new ObjectId();
			put("_id", objectId);
		}
		collection.insert(array, collection.getWriteConcern(), new JsonDBEncoder());
	}

	/**
	 * Converts a JsonDBObject to a map.
	 * @return the map representative
	 */
	public Map<String,JsonValue> toMap() {
		return new LinkedHashMap<String,JsonValue>(this);
	}

	/**
	 * toString: creates a string representative from the instance.
	 * @return json format from the instance
	 */
	@Override
	public String toString() {
		return ParserHelper.jsonDBObjectToString(this);
	}
}
