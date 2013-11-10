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

import java.util.List;

public class ListObject {
	private int id;
	private String name;
	private List<SimpleObject> list;
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
	public List<SimpleObject> getList() {
		return list;
	}
	public void setList(List<SimpleObject> list) {
		this.list = list;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ListObject) {
			ListObject object = (ListObject)obj;
			if(id != object.id || !name.equalsIgnoreCase(object.name)) {
				return false;
			}
			for(int i = 0; i < list.size(); i++) {
				if(!list.get(i).equals(object.list.get(i))) {
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
		for(int i = 0; i < list.size(); i++) {
			hashCode += list.get(i).hashCode();
		}
		return hashCode;
	}
}
