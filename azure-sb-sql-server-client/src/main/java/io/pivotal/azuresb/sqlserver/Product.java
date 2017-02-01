package io.pivotal.azuresb.sqlserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	protected Product() {
	}

	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("Product[id=%d, name='%s', description='%s']", id,
				name, description);
	}
}
