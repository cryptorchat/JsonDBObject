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

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.bson.types.ObjectId;

import com.mongodb.DefaultDBEncoder;

/**
 * JsonDBEncoder
 * Encode JsonValue into standard field.
 */
public class JsonDBEncoder extends DefaultDBEncoder {
	@Override
	protected void _putObjectField(final String name, final Object val) {
		if("_id".equalsIgnoreCase(name)) {
			final ObjectId objectId = new ObjectId(val.toString());
			super._putObjectField(name, objectId);
		} else
		if(val instanceof JsonValue) {
			final JsonValue value = (JsonValue)val;
			if(value == JsonValue.NULL) {
				super._putObjectField(name, null);
			} else
			if(value == JsonValue.TRUE) {
				super._putObjectField(name, true);
			} else
			if (value == JsonValue.FALSE) {
				super._putObjectField(name, false);
			} else
			if(value instanceof JsonNumber) {
				final JsonNumber number = (JsonNumber)value;
				super._putObjectField(name, number.longValue());
			} else
			if(value instanceof JsonString) {
				final JsonString str = (JsonString)value;
				super._putObjectField(name, str.getString());
			} else
			if(value instanceof JsonObject || value instanceof JsonArray) {
				super._putObjectField(name, value);
			} else {
				super._putObjectField(name, value.toString());
			}
		}
		else {
			super._putObjectField(name, val);
		}
	}
}
