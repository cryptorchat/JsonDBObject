Hybrid Implementation of javax.json JsonObject mongoDB's DBObject interface.
============================================================================

Quick start
-----------

This is what using the mapper looks like:

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


