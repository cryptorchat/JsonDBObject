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

public class ComplexObject {
	private int id;
	private String name;
	private SimpleObject simple;
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
	public SimpleObject getSimple() {
		return simple;
	}
	public void setSimple(SimpleObject simple) {
		this.simple = simple;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ComplexObject) {
			ComplexObject complex = (ComplexObject)obj;
			if(id == complex.id && name.equalsIgnoreCase(complex.name) && simple.equals(complex.simple)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return id + name.hashCode() + simple.hashCode();
	}
}
