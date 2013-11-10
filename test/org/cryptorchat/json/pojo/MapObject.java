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

import java.util.Iterator;
import java.util.Map;

public class MapObject {
	private int id;
	private String name;
	private Map<String, SimpleObject> map;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, SimpleObject> getMap() {
		return map;
	}
	public void setMap(Map<String, SimpleObject> map) {
		this.map = map;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MapObject) {
			MapObject object = (MapObject)obj;
			if(id != object.id || !name.equalsIgnoreCase(object.name)) {
				return false;
			}
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				SimpleObject object1 = map.get(key);
				SimpleObject object2 = object.map.get(key);
				if(!object1.equals(object2)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		int hashCode = id + name.hashCode();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			hashCode += map.get(key).hashCode();
		}
		return hashCode;
	}
}
