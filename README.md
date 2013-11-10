JsonDBObject README
-------------------

Welcome to CryptorChat Api!

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



LICENSE
-------
  Most CryptorChat source files (including JsonDBObject) are made available under the terms of the
  Apache License Version 2.0.  See individual files for details.
