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
package org.cryptorchat.json.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static boolean init = false;
	private static final Properties prop = new Properties();

	public static void init() {
		try {
			prop.load(new FileInputStream("config.properties"));
			init = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getMongoHost() {
		if(!init) {
			init();
		}
		return prop.getProperty("mongoHost");
	}

	public static int getMongoPort() {
		if(!init) {
			init();
		}
		String port = prop.getProperty("mongoPort");
		return Integer.valueOf(port);
	}

	public static String getMongoDB() {
		if(!init) {
			init();
		}
		return prop.getProperty("mongoDB");
	}

	public static String getMongoUser() {
		if(!init) {
			init();
		}
		return prop.getProperty("mongoUser");
	}

	public static char[] getMongoPass() {
		if(!init) {
			init();
		}
		String pass = prop.getProperty("mongoPass");
		return pass.toCharArray();
	}
}
