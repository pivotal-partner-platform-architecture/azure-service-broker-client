package io.pivotal.azuresb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AzureSbClientApplication {

	private static final Logger log = LoggerFactory.getLogger(AzureSbClientApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AzureSbClientApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(ProductRepository repository) {
		return (args) -> {
			
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
			
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Product p : repository.findAll()) {
				id = p.getId();
				log.info(p.toString());
			}
			log.info("");

			Product product = repository.findOne(id);
			log.info("Product found with findOne(id):");
			log.info("--------------------------------");
			log.info(product.toString());
			log.info("");

			// fetch customers by last name
			log.info("Product found with findByName('pcf'):");
			log.info("--------------------------------------------");
			for (Product p : repository.findByName("pcf")) {
				log.info(p.toString());
			}
			log.info("");
		};
	}	  
}
