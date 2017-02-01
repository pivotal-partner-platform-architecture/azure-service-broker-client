package io.pivotal.azuresb.sqlserver;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findByName(String name);
}
