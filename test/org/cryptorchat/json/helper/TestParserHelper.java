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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.cryptorchat.json.JsonDBObject;
import org.cryptorchat.json.helper.ParserHelper;
import org.cryptorchat.json.pojo.ComplexObject;
import org.cryptorchat.json.pojo.ListObject;
import org.cryptorchat.json.pojo.MapObject;
import org.cryptorchat.json.pojo.ObjectGenerator;
import org.cryptorchat.json.pojo.SimpleObject;
import org.junit.Test;

public class TestParserHelper {
	@Test
	public void testPojoToString() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();
		simple.setTitle(null);

		String str = ParserHelper.pojoToString(simple);
		assertEquals("{\"id\":1,\"number\":123,\"name\":\"test\",\"title\":null,\"value\":true}", str);
	}

	@Test
	public void testWithPrimitives() {
		JsonValue json1 = ParserHelper.parseToJsonValue(true);
		assertEquals(ValueType.TRUE, json1.getValueType());

		JsonValue json2 = ParserHelper.parseToJsonValue(false);
		assertEquals(ValueType.FALSE, json2.getValueType());

		JsonValue json3 = ParserHelper.parseToJsonValue("test");
		assertEquals(ValueType.STRING, json3.getValueType());
		JsonString string = (JsonString)json3;
		assertEquals("test", string.getString());

		JsonValue json4 = ParserHelper.parseToJsonValue(12345);
		assertEquals(ValueType.NUMBER, json4.getValueType());
		JsonNumber number = (JsonNumber)json4;
		assertEquals(12345, number.intValue());
	}

	@Test
	public void testWithSimpleObject() {
		SimpleObject simple = ObjectGenerator.createSimpleObject();
		simple.setTitle(null);
		
		JsonValue json = ParserHelper.parseToJsonValue(simple);
		assertNotNull(json);
		assertEquals(ValueType.OBJECT, json.getValueType());
		
		JsonDBObject jsonDBObject = (JsonDBObject)json;
		assertEquals("test",	jsonDBObject.getString("name"));
		assertEquals(1,			jsonDBObject.getInt("id"));
		assertEquals(123,		jsonDBObject.getInt("number"));

		assertEquals("{\"id\":1,\"name\":\"test\",\"number\":123,\"title\":null,\"value\":true}", jsonDBObject.toString());
	}

	@Test
	public void testWithComplexObject() {
		ComplexObject complex = ObjectGenerator.createComplexObject();

		JsonValue json = ParserHelper.parseToJsonValue(complex);
		assertNotNull(json);
		assertEquals(ValueType.OBJECT, json.getValueType());

		JsonDBObject jsonDBObject		= (JsonDBObject)json;

		assertEquals("{\"id\":2,"
				+ "\"name\":\"complex\","
				+ "\"simple\":{\"id\":1,"
							+ "\"number\":123,"
							+ "\"name\":\"test\","
							+ "\"title\":\"TestTitle\","
							+ "\"value\":true}}", jsonDBObject.toString());
	}

	@Test
	public void testWithList() {
		List<SimpleObject> list = ObjectGenerator.createSimpleObjectList();

		JsonValue json = ParserHelper.parseToJsonValue(list);
		assertEquals(ValueType.ARRAY, json.getValueType());

		JsonArray array = (JsonArray)json;
		assertEquals(2, array.size());
	}

	@Test
	public void testWithListObject() {
		ListObject object = ObjectGenerator.createListObject();

		JsonValue json = ParserHelper.parseToJsonValue(object);
		assertEquals(ValueType.OBJECT, json.getValueType());

		JsonDBObject jsonDBObject		= (JsonDBObject)json;

		assertEquals("{\"id\":100,"
					+ "\"list\":[{\"id\":1,"
							   + "\"number\":123,"
							   + "\"name\":\"test\","
							   + "\"title\":\"TestTitle\","
							   + "\"value\":true},"
							  + "{\"id\":2,"
							   + "\"number\":123,"
							   + "\"name\":\"second test\","
							   + "\"title\":\"TestTitle\","
							   + "\"value\":true}],"
					+ "\"name\":\"testListObject\"}", jsonDBObject.toString());
	}

	@Test
	public void testWithMap() {
		Map<String, SimpleObject> map = ObjectGenerator.createSimpleObjectMap();

		JsonValue json = ParserHelper.parseToJsonValue(map);
		assertEquals(ValueType.ARRAY, json.getValueType());

		JsonArray array = (JsonArray)json;
		assertEquals(2, array.size());
	}

	@Test
	public void testWithMapObject() {
		MapObject map = ObjectGenerator.createMapObject();

		JsonValue json = ParserHelper.parseToJsonValue(map);
		assertEquals(ValueType.OBJECT, json.getValueType());

		JsonDBObject jsonDBObject		= (JsonDBObject)json;

		assertEquals("{\"id\":100,"
					+ "\"map\":{\"key2\":{\"id\":2,"
									   + "\"number\":123,"
									   + "\"name\":\"second test\","
									   + "\"title\":\"TestTitle\","
									   + "\"value\":true},"
							+  "\"key1\":{\"id\":1,"
									   + "\"number\":123,"
									   + "\"name\":\"test\","
									   + "\"title\":\"TestTitle\","
									   + "\"value\":true}},"
							+  "\"name\":\"testMapObject\"}", jsonDBObject.toString());
	}
}
