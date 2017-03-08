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

package io.pivotal.azuresb.docdb;

import io.pivotal.azuresb.docdb.dao.TodoDao;
import io.pivotal.azuresb.docdb.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author pbopardikar This is the REST Controller class that implements all the
 *         REST CRUD methods for the DocumentDB
 */
@RestController
public class DocumentDbRestController {

	private final TodoDao todoDao;

	public DocumentDbRestController(
			@Qualifier("DocDbDaoInstance") TodoDao todoDao) {
		this.todoDao = todoDao;
	}

	@RequestMapping("/greeting")
	private List<String> greet(String name) {
		System.out.println("Dummy Test Method");
		List<String> greetings = new ArrayList<String>();
		greetings.add("Hello " + name + "!");
		greetings.add("Hola " + name + "!");
		greetings.add("Namaste " + name + "!");
		greetings.add("Bon Jour " + name + "!");
		return greetings;
	}

	@RequestMapping("/read")
	private List<TodoItem> readTodoItems() {
		List<TodoItem> todoItems = null;
		try {
			todoItems = todoDao.readTodoItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoItems;
	}

	@RequestMapping("/write")
	private TodoItem createTodoItem(String name, String category) {
		TodoItem todoItemCreated = null;
		TodoItem todoItem = new TodoItem();
		todoItem.setId(String.valueOf(System.currentTimeMillis()));
		todoItem.setComplete(false);
		todoItem.setCategory(category);
		todoItem.setName(name);
		try {
			todoItemCreated = todoDao.createTodoItem(todoItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoItemCreated;
	}

	@RequestMapping("/update")
	private TodoItem updateTodoItem(String id, boolean isComplete) {
		TodoItem todoItemUpdated = null;
		try {
			todoItemUpdated = todoDao.updateTodoItem(id, isComplete);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoItemUpdated;
	}

	@RequestMapping("/delete")
	private boolean deleteTodoItem(String id) {
		boolean deleted = false;
		try {
			deleted = todoDao.deleteTodoItem(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleted;
	}

}
