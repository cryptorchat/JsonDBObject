JsonDBObject README
-------------------

Welcome to CryptorChat Api!

Links
-----
Homepage: [http://api.cryptorchat.org](http://api.cryptorchat.org)

[Indiegogo support page](http://www.indiegogo.com/projects/cryptorchat)


USAGE
-----
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String inputJson = request.getParameter("inputJson");
		JsonDBObject jsonObject = new JsonDBObject(inputJson);

		// Validation...

		// Write the object into the Collection
		jsonObject.insertToCollection(collection);

		// Response with all record from the collection
		DBCursor cursor = collection.find();
		JsonDBObject allItemInCollection = new JsonDBObject(cursor);

		// Set the response content type to JSON
		response.setContentType("application/json");	

		// Write the object
		JsonWriter jsonWriter = Json.createWriter(response.getWriter());
		jsonWriter.writeObject(allItemInCollection);
		jsonWriter.close();
	}


Test
----
To execute the "mvn test" copy the config.properties.template to config.properties and setup your mongodb parameters.


LICENSE
-------
  Most CryptorChat source files (including JsonDBObject) are made available under the terms of the
  Apache License Version 2.0.  See individual files for details.
