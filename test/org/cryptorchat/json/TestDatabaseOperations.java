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

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bson.types.ObjectId;
import org.cryptorchat.json.JsonDBObject;
import org.cryptorchat.json.db.DatabaseHandler;
import org.cryptorchat.json.pojo.BasicObject;
import org.cryptorchat.json.pojo.ComplexObject;
import org.cryptorchat.json.pojo.ListObject;
import org.cryptorchat.json.pojo.MapObject;
import org.cryptorchat.json.pojo.ObjectGenerator;
import org.cryptorchat.json.pojo.ObjectWithInnerList;
import org.cryptorchat.json.pojo.SimpleObject;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class TestDatabaseOperations {
	@Test
	public void testWithBasicObject() throws FileNotFoundException, IOException {
		cleanCollection();

		BasicObject basic1 = ObjectGenerator.createBasicObject();

		JsonDBObject object1 = new JsonDBObject(basic1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		BasicObject basic2 = object2.getPOJO(BasicObject.class);

		assertTrue(basic2.equals(basic1));
	}

	@Test
	public void testWithSimpleObject() throws FileNotFoundException, IOException {
		cleanCollection();

		SimpleObject simple1 = ObjectGenerator.createSimpleObject();

		JsonDBObject object1 = new JsonDBObject(simple1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		SimpleObject simple2 = object2.getPOJO(SimpleObject.class);

		assertTrue(simple2.equals(simple1));
	}

	@Test
	public void testWithComplexObject() throws FileNotFoundException, IOException {
		cleanCollection();

		ComplexObject complex1 = ObjectGenerator.createComplexObject();

		JsonDBObject object1 = new JsonDBObject(complex1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		ComplexObject complex2 = object2.getPOJO(ComplexObject.class);

		assertTrue(complex2.equals(complex1));
	}

	@Test
	public void testWithListObject() throws FileNotFoundException, IOException {
		cleanCollection();

		ListObject list1 = ObjectGenerator.createListObject();

		JsonDBObject object1 = new JsonDBObject(list1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		ListObject list2 = object2.getPOJO(ListObject.class);

		assertTrue(list2.equals(list1));
	}

	@Test
	public void testWithMapObject() throws FileNotFoundException, IOException {
		cleanCollection();

		MapObject map1 = ObjectGenerator.createMapObject();

		JsonDBObject object1 = new JsonDBObject(map1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		MapObject map2 = object2.getPOJO(MapObject.class);

		assertTrue(map2.equals(map1));
	}

	@Test
	public void testWithObjectWithInnerList() throws FileNotFoundException, IOException {
		cleanCollection();

		ObjectWithInnerList list1 = ObjectGenerator.createObjectWithInnerList();

		JsonDBObject object1 = new JsonDBObject(list1);

		DBCollection collection = DatabaseHandler.getCollection();

		object1.insertToCollection(collection);

		ObjectId id = object1.getObjectId();
		JsonDBObject object2 = new JsonDBObject(collection, id);

		ObjectWithInnerList list2 = object2.getPOJO(ObjectWithInnerList.class);

		assertTrue(list2.equals(list1));
	}

	public void cleanCollection() throws FileNotFoundException, IOException {
		DBCollection coll = DatabaseHandler.getCollection();
		coll.remove(new BasicDBObject());
	}
}
