package io.pivotal.azuresb.sqlserver;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlRestController {

	private static final Logger LOG = LoggerFactory.getLogger(SqlRestController.class);
	private static final String CR = "</BR>";
	
	@Autowired
	private ProductRepository repository;

	@RequestMapping(value = "/sql", method = RequestMethod.GET)
	public String processSql() {
		
		LOG.info("SqlRestController processSql start...");
		StringBuffer result = new StringBuffer();
		
		if (repository.findByName("pcf").size() < 1)
		{
			repository.save(new Product("pcf", "pivotal cloud foundry"));
		}

		if (repository.findByName("azure").size() < 1)
		{
			repository.save(new Product("azure", "microsoft azure cloud"));
		}

		// fetch all customers
		long id = 0L;
		
		result.append("Customers found with findAll():" + CR);
		result.append("-------------------------------" + CR);
		for (Product p : repository.findAll()) {
			id = p.getId();
			result.append(p.toString() + CR);
		}
		result.append("" + CR);

		Product product = repository.findOne(id);
		result.append("Product found with findOne(id):" + CR);
		result.append("--------------------------------" + CR);
		result.append(product.toString() + CR);
		result.append("" + CR);

		// fetch customers by last name
		result.append("Product found with findByName('pcf'):" + CR);
		result.append("--------------------------------------------" + CR);
		for (Product p : repository.findByName("pcf")) {
			result.append(p.toString() + CR);
		}
		result.append("" + CR);
		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("SqlRestController processSql end");
		return result.toString();
	}
	
	
}
