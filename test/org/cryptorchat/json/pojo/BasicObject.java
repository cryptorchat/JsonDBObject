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

public class BasicObject {
	private String _id;
	private String name;
	private int value;
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BasicObject) {
			BasicObject basic = (BasicObject)obj;
			if (_id == basic._id &&
				name.equalsIgnoreCase(basic.name) &&
				value == basic.value) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return _id.hashCode() + name.hashCode() + value;
	}
}
