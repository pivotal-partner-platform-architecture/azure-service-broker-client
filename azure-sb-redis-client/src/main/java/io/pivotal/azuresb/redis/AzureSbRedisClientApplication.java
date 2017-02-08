package io.pivotal.azuresb.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzureSbRedisClientApplication {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(AzureSbRedisClientApplication.class, args);
	}
}
