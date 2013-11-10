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

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DatabaseHandler {
	/**
	 * client: the MongoDB Client
	 */
	private static MongoClient client;

	/**
	 * db: the MongoDB Database
	 */
	private static DB database;

	/**
	 * init
	 * This function initialize the client and the database.
	 * This function require the Config class to be initialized.
	 * @return if the initialization successful returns true, otherwise false.
	 * @throws UnknownHostException 
	 */
	public static void init() throws UnknownHostException {
		synchronized(DatabaseHandler.class) {
			if(client == null) {														// If not initialized yet.
		
				client				= new MongoClient(Config.getMongoHost(), Config.getMongoPort());		// Connect to the database
				database			= client.getDB(Config.getMongoDB());									// Get the database
				final boolean auth	= database.authenticate(Config.getMongoUser(), Config.getMongoPass());	// Authenticate
	
				if(!auth) {															// If authentication failed
					throw new RuntimeException("Database Authentication error");
				}
			}
		}
	}

	/**
	 * getMongoClient
	 * If not initialized, throws an exception.
	 * @return returns the client
	 * @throws UnknownHostException 
	 */
	public static MongoClient getMongoClient() throws UnknownHostException {
		synchronized (DatabaseHandler.class) {
			if(client == null) {								// If not initialized
				init();
			}
			return client;										// Otherwise return the client
		}
	}

	/**
	 * getDatabase
	 * If not initialized, throws an exception.
	 * @return returns the database
	 * @throws UnknownHostException 
	 */
	public static DB getDatabase() throws UnknownHostException {
		synchronized (DatabaseHandler.class) {
			if(database == null) {								// If not initialized
				init();
			}
			return database;									// Otherwise return the database
		}
	}

	/**
	 * getCollection
	 * @return the JsonDBObject Collection
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollection() throws UnknownHostException {
		synchronized (DatabaseHandler.class) {
			if(database == null) {
				init();
			}
			return database.getCollection( "JsonDBObject" );	// Get the collection
		}
	}
}
