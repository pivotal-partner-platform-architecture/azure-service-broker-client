/**
 Copyright (C) 2017-Present Pivotal Software, Inc. All rights reserved.

 This program and the accompanying materials are made available under
 the terms of the under the Apache License, Version 2.0 (the "License”);
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package io.pivotal.ecosystem.azure.docdb.dao;

import io.pivotal.ecosystem.azure.autoconfigure.AzureDocumentDBProperties;
import io.pivotal.ecosystem.azure.docdb.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;

@Component("DocDbDaoInstance")
public class DocDbDao implements TodoDao {

	// Use Gson for POJO <=> JSON serialization
	private static Gson gson = new Gson();

	private final AzureDocumentDBProperties properties;
	private final DocumentClient documentClient;

	// Cache for the database object, so we don't have to query for it to
	// retrieve self links.
	private static Database databaseCache;

	// Cache for the collection object, so we don't have to query for it to
	// retrieve self links.
	private static DocumentCollection collectionCache;

	public DocDbDao(AzureDocumentDBProperties properties, DocumentClient documentClient) {
		this.properties = properties;
		this.documentClient = documentClient;
	}

	@Override
	public TodoItem createTodoItem(TodoItem todoItem) {
		// Serialize the TodoItem as a JSON Document.
		Document todoItemDocument = new Document(gson.toJson(todoItem));

		// Annotate the document as a TodoItem for retrieval (so that we can
		// store multiple entity types in the collection).
		todoItemDocument.set("entityType", "todoItem");

		try {
			// Persist the document using the DocumentClient.
			todoItemDocument = documentClient.createDocument(
					getTodoCollection().getSelfLink(), todoItemDocument, null,
					false).getResource();
		} catch (DocumentClientException e) {
			e.printStackTrace();
			return null;
		}

		return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
	}

	@Override
	public TodoItem readTodoItem(String id) {
		// Retrieve the document by id using our helper method.
		Document todoItemDocument = getDocumentById(id);

		if (todoItemDocument != null) {
			// De-serialize the document in to a TodoItem.
			return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
		} else {
			return null;
		}
	}

	@Override
	public List<TodoItem> readTodoItems() {
		List<TodoItem> todoItems = new ArrayList<TodoItem>();
		// Retrieve the TodoItem documents
		List<Document> documentList = documentClient
				.queryDocuments(getTodoCollection().getSelfLink(),
						"SELECT * FROM root r WHERE r.entityType = 'todoItem'",
						null).getQueryIterable().toList();

		// De-serialize the documents in to TodoItems.
		for (Document todoItemDocument : documentList) {
			todoItems.add(gson.fromJson(todoItemDocument.toString(),
					TodoItem.class));
		}
		return todoItems;
	}

	@Override
	public TodoItem updateTodoItem(String id, boolean isComplete) {
		// Retrieve the document from the database
		Document todoItemDocument = getDocumentById(id);

		// You can update the document as a JSON document directly.
		// For more complex operations - you could de-serialize the document in
		// to a POJO, update the POJO, and then re-serialize the POJO back in to
		// a document.
		todoItemDocument.set("complete", isComplete);

		try {
			// Persist/replace the updated document.
			todoItemDocument = documentClient.replaceDocument(todoItemDocument,
					null).getResource();
		} catch (DocumentClientException e) {
			e.printStackTrace();
			return null;
		}

		return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
	}

	@Override
	public boolean deleteTodoItem(String id) {
		// DocumentDB refers to documents by self link rather than id.

		// Query for the document to retrieve the self link.
		Document todoItemDocument = getDocumentById(id);

		try {
			// Delete the document by self link.
			documentClient.deleteDocument(todoItemDocument.getSelfLink(), null);
		} catch (DocumentClientException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private Database getTodoDatabase() {

		if (databaseCache == null) {
			// Get the database if it exists
			List<Database> databaseList = documentClient
					.queryDatabases(
							"SELECT * FROM root r WHERE r.id='"
									+ properties.getDatabaseId()
									+ "'", null).getQueryIterable().toList();

			if (databaseList.size() > 0) {
				// Cache the database object so we won't have to query for it
				// later to retrieve the selfLink.
				databaseCache = databaseList.get(0);
			} else {
				// Create the database if it doesn't exist.
				try {
					Database databaseDefinition = new Database();
					databaseDefinition.setId(properties
							.getDatabaseId());

					databaseCache = documentClient.createDatabase(
							databaseDefinition, null).getResource();
				} catch (DocumentClientException e) {
					// TODO: Something has gone terribly wrong - the app wasn't
					// able to query or create the collection.
					// Verify your connection, endpoint, and key.
					e.printStackTrace();
				}
			}
		}

		return databaseCache;
	}

	private DocumentCollection getTodoCollection() {

		if (collectionCache == null) {
			// Get the collection if it exists.

			List<DocumentCollection> collectionList = documentClient
					.queryCollections(
							getTodoDatabase().getSelfLink(),
							"SELECT * FROM root r WHERE r.id='"
									+ properties.getResourceId()
									+ "'", null).getQueryIterable().toList();

			if (collectionList.size() > 0) {
				// Cache the collection object so we won't have to query for it
				// later to retrieve the selfLink.
				collectionCache = collectionList.get(0);
			} else {
				// Create the collection if it doesn't exist.
				try {
					DocumentCollection collectionDefinition = new DocumentCollection();
					collectionDefinition.setId(properties
							.getResourceId());

					collectionCache = documentClient.createCollection(
							getTodoDatabase().getSelfLink(),
							collectionDefinition, null).getResource();
				} catch (DocumentClientException e) {
					// TODO: Something has gone terribly wrong - the app wasn't
					// able to query or create the collection.
					// Verify your connection, endpoint, and key.
					e.printStackTrace();
				}
			}
		}

		return collectionCache;
	}

	private Document getDocumentById(String id) {
		// Retrieve the document using the DocumentClient.
		List<Document> documentList = documentClient
				.queryDocuments(getTodoCollection().getSelfLink(),
						"SELECT * FROM root r WHERE r.id='" + id + "'", null)
				.getQueryIterable().toList();

		if (documentList.size() > 0) {
			return documentList.get(0);
		} else {
			return null;
		}
	}

}