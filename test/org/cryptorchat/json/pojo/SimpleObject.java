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

public class SimpleObject {
	private int id;
	private Integer number;
	private String name;
	private String title;
	private boolean value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SimpleObject) {
			SimpleObject simple = (SimpleObject)obj;
			if (id == simple.id &&
				number.equals(simple.number) &&
				name.equalsIgnoreCase(simple.name) &&
				title.equalsIgnoreCase(simple.title) &&
				value == simple.value) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return id + number.hashCode() + name.hashCode() + title.hashCode();
	}
}
