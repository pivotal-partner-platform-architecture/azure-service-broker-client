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

import io.pivotal.ecosystem.azure.docdb.model.TodoItem;

import java.util.List;

public interface TodoDao {
	/**
	 * @return A list of TodoItems
	 */
	public List<TodoItem> readTodoItems();

	/**
	 * @param todoItem
	 * @return whether the todoItem was persisted.
	 */
	public TodoItem createTodoItem(TodoItem todoItem);

	/**
	 * @param id
	 * @return the TodoItem
	 */
	public TodoItem readTodoItem(String id);

	/**
	 * @param id
	 * @return the TodoItem
	 */
	public TodoItem updateTodoItem(String id, boolean isComplete);

	/**
	 *
	 * @param id
	 * @return whether the delete was successful.
	 */
	public boolean deleteTodoItem(String id);
}