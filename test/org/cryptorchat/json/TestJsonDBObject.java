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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.cryptorchat.json.JsonDBObject;
import org.cryptorchat.json.helper.ParserHelper;
import org.cryptorchat.json.pojo.ObjectGenerator;
import org.cryptorchat.json.pojo.SimpleObject;

import org.junit.Test;

public class TestJsonDBObject {

	@Test
	public void testConstructorWithString() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject stringGenerator = new JsonDBObject(simple);
		String jsonString = stringGenerator.toString();

		JsonDBObject object = new JsonDBObject(jsonString);

		assertEquals("{\"id\":1,\"name\":\"test\",\"number\":123,\"title\":\"TestTitle\",\"value\":true}", object.toString());

		assertEquals(1,				object.getInt("id"));
		assertEquals("test",		object.getString("name"));
		assertEquals(123,			object.getInt("number"));
		assertEquals("TestTitle",	object.getString("title"));
		assertEquals(true,			object.getBoolean("value"));
	}

	@Test
	public void testConstructorWithKeyValue() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();
		simple.setValue(false);

		JsonValue value = ParserHelper.parseToJsonValue(simple);
		JsonDBObject object = new JsonDBObject("key", value);
		assertEquals("{\"key\":{\"id\":1,"
							 + "\"name\":\"test\","
							 + "\"number\":123,"
							 + "\"title\":\"TestTitle\","
							 + "\"value\":false}}", object.toString());

		JsonDBObject inner = object.getJsonObject("key");
		assertNotNull(inner);
		assertEquals(1,				inner.getInt("id"));
		assertEquals("test",		inner.getString("name"));
		assertEquals(123,			inner.getInt("number"));
		assertEquals("TestTitle",	inner.getString("title"));
		assertEquals(false,			inner.getBoolean("value"));
	}

	@Test
	public void testConstructorWithMap() {
		SimpleObject simple1 = ObjectGenerator.createSimpleObject();

		SimpleObject simple2 = ObjectGenerator.createSimpleObject();
		simple2.setId(112);
		simple2.setName("test2");

		Map<String, JsonValue> map = new HashMap<>();
		map.put("key1", ParserHelper.parseToJsonValue(simple1));
		map.put("key2", ParserHelper.parseToJsonValue(simple2));

		JsonDBObject object = new JsonDBObject(map);

		assertEquals("{}", object.toString());

		object = new JsonDBObject("key", map);
		
		JsonArray array = object.getJsonArray("key");
		JsonObject object1 = array.getJsonObject(0);
		assertEquals("test2", object1.getJsonObject("key2").getString("name") );
	}

	@Test
	public void testConstructorCopy() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject original = new JsonDBObject(simple);

		JsonDBObject copy = new JsonDBObject(original);
		assertEquals(1,				copy.getInt("id"));
		assertEquals("test",		copy.getString("name"));
		assertEquals(123,			copy.getInt("number"));
		assertEquals("TestTitle",	copy.getString("title"));
		assertEquals(true,			copy.getBoolean("value"));
	}

	@Test
	public void testConstructorWithKeyObject() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject object = new JsonDBObject("key", simple);
		JsonDBObject inner = object.getJsonObject("key");
		assertEquals(1,				inner.getInt("id"));
		assertEquals("test",		inner.getString("name"));
		assertEquals(123,			inner.getInt("number"));
		assertEquals("TestTitle",	inner.getString("title"));
		assertEquals(true,			inner.getBoolean("value"));
	}

	@Test
	public void testAppend() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject object = new JsonDBObject();
		JsonDBObject append = object.append("key", simple);
		assertTrue(object == append);
	}

	@Test
	public void testClone() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject object = new JsonDBObject(simple);
		JsonDBObject clone = object.clone();
		assertTrue(object != clone);
	}

	@Test
	public void testGetPOJO() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();

		JsonDBObject object = new JsonDBObject(simple);
		SimpleObject pojo = object.getPOJO(SimpleObject.class);
		assertNotNull(pojo);
	}
}
