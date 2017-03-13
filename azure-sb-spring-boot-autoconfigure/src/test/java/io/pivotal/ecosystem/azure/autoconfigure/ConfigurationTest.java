package io.pivotal.ecosystem.azure.autoconfigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

@ActiveProfiles( profiles = "testing" )
public class ConfigurationTest
{
	
	@Autowired
	private AzureStorageProperties storageProperties;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test()
	{
		assertNotNull(storageProperties);
		System.out.println("************************************************************");
		System.out.println("");
		System.out.println(storageProperties.toString());
		System.out.println("");
		System.out.println("************************************************************");
		assertEquals("TestingAccount", storageProperties.getName());
	}

}
