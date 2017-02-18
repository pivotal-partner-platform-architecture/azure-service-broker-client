package io.pivotal.azuresb.docdb;

import io.pivotal.azuresb.docdb.dao.TodoDao;
import io.pivotal.azuresb.docdb.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
// @ComponentScan("io.pivotal")
public class AzureSbDocumentdbClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureSbDocumentdbClientApplication.class, args);
	}

	@Autowired
	@Qualifier("DocDbDaoInstance")
	private TodoDao todoDao;

	@RequestMapping("/greeting")
	List<String> greet(String name) {
		List<String> greetings = new ArrayList<String>();
		greetings.add("Hello " + name + "!");
		greetings.add("Hola " + name + "!");
		greetings.add("Namaste " + name + "!");
		return greetings;
	}

	@RequestMapping("/read")
	List<TodoItem> readTodoItems() {
		List<TodoItem> todoItems = null;
		System.out.println("\nBEGIN\n");
		try {
			todoItems = todoDao.readTodoItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n*** Dao Invoked \n");
		System.out.println("\nRESULTS:\n" + todoItems);
		return todoItems;
	}

	@RequestMapping("/write")
	TodoItem createTodoItem(String name, String category) {
		TodoItem todoItemCreated = null;
		TodoItem todoItem = new TodoItem();
		todoItem.setId(String.valueOf(System.currentTimeMillis()));
		todoItem.setComplete(false);
		todoItem.setCategory(category);
		todoItem.setName(name);
		System.out.println("\nBEGIN\n");
		try {
			todoItemCreated = todoDao.createTodoItem(todoItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n*** Dao Invoked \n");
		System.out.println("\nRESULTS:\n" + todoItemCreated);
		return todoItemCreated;
	}

	@RequestMapping("/update")
	TodoItem updateTodoItem(String id, boolean isComplete) {
		TodoItem todoItemUpdated = null;
		System.out.println("\nBEGIN\n");
		try {
			todoItemUpdated = todoDao.updateTodoItem(id, isComplete);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n*** Dao Invoked \n");
		System.out.println("\nRESULTS:\n" + todoItemUpdated);
		return todoItemUpdated;
	}

	@RequestMapping("/delete")
	boolean deleteTodoItem(String id) {
		boolean deleted = false;
		System.out.println("\nBEGIN\n");
		try {
			deleted = todoDao.deleteTodoItem(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n*** Dao Invoked \n");
		System.out.println("\nRESULTS:\n" + deleted);
		return deleted;
	}

}
