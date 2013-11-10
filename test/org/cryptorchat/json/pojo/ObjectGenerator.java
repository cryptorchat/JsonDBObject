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
package org.cryptorchat.json.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectGenerator {
	public static BasicObject createBasicObject() {
		BasicObject basic = new BasicObject();
		basic.setName("basic");
		basic.setValue(256);
		return basic;
	}

	public static SimpleObject createSimpleObject() {
		SimpleObject object = new SimpleObject();
		object.setId(1);
		object.setName("test");
		object.setNumber(123);
		object.setTitle("TestTitle");
		object.setValue(true);
		return object;
	}

	public static ComplexObject createComplexObject() {
		ComplexObject complex = new ComplexObject();
		complex.setId(2);
		complex.setName("complex");
		complex.setSimple(createSimpleObject());
		return complex;
	}

	public static List<SimpleObject> createSimpleObjectList() {
		SimpleObject simple1 = ObjectGenerator.createSimpleObject();
		SimpleObject simple2 = ObjectGenerator.createSimpleObject();

		simple2.setId(2);
		simple2.setName("second test");

		List<SimpleObject> list = new ArrayList<>();
		list.add(simple1);
		list.add(simple2);

		return list;
	}

	public static ListObject createListObject() {
		ListObject object = new ListObject();
		object.setId(100);
		object.setName("testListObject");
		object.setList(createSimpleObjectList());

		return object;
	}

	public static Map<String, SimpleObject> createSimpleObjectMap() {
		SimpleObject simple1 = ObjectGenerator.createSimpleObject();
		SimpleObject simple2 = ObjectGenerator.createSimpleObject();

		simple2.setId(2);
		simple2.setName("second test");

		Map<String, SimpleObject> map = new HashMap<>();
		map.put("key1", simple1);
		map.put("key2", simple2);

		return map;
	}

	public static MapObject createMapObject() {
		MapObject map = new MapObject();
		map.setId(100);
		map.setName("testMapObject");
		map.setMap(createSimpleObjectMap());
		return map;
	}

	public static ObjectWithInnerList createObjectWithInnerList() {
		ObjectWithInnerList list = new ObjectWithInnerList();
		List<ListObject> innerList = new ArrayList<>();
		innerList.add(createListObject());
		innerList.add(createListObject());
		list.setList(innerList);
		return list;
	}
}
