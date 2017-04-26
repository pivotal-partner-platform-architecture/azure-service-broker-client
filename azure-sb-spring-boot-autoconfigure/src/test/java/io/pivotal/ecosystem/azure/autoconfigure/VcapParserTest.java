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

package io.pivotal.ecosystem.azure.autoconfigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={VcapParser.class})
@ActiveProfiles( profiles = "testing" )
public class VcapParserTest
{
	private static final Logger LOG = LoggerFactory.getLogger(VcapParserTest.class);

	@Autowired
	private VcapParser parser;
	
	@Test
	public void testVcapSingleService()
	{
		Resource resource = new ClassPathResource("/vcap1.json");
		String content;
		try
		{
			content = new String(Files.readAllBytes(Paths.get(resource.getURI())));
			VcapResult result = parser.parse(content); 
			VcapPojo[] pojos = result.getPojos();
			assertNotNull(pojos);
			assertEquals(1, pojos.length);
			VcapPojo pojo = pojos[0];
			
			LOG.debug("pojo = " + pojo);
			assertEquals(5, pojo.getCredentials().size());
			assertEquals(2, pojo.getTags().length);
			assertEquals(0, pojo.getVolumeMounts().length);
			assertEquals("azure-sqldb", pojo.getLabel());
			assertEquals("provider", pojo.getProvider());
			assertEquals("azure-sqldb", pojo.getServiceBrokerName());
			assertEquals("myazuredb-service", pojo.getServiceInstanceName());
			assertEquals("basic", pojo.getServicePlan());
			assertEquals("drain_url", pojo.getSyslogDrainUrl());
			assertEquals("Azure", pojo.getTags()[0]);
			assertEquals("SQL", pojo.getTags()[1]);
			
			assertEquals("userid", pojo.getCredentials().get("administratorLogin"));
			assertEquals("password", pojo.getCredentials().get("administratorLoginPassword"));
			assertEquals("jdbc:sqlserver://hostname:1433;database=dbname;user=user;password=pw", pojo.getCredentials().get("jdbcUrl"));
			assertEquals("sql-server-name", pojo.getCredentials().get("sqlServerName"));
			assertEquals("db-name", pojo.getCredentials().get("sqldbName"));
		} 
		catch (IOException e)
		{
			LOG.error("Error reading json file", e);
		}
	}
	
	@Test
	public void testVcapSingleServiceWithNulls()
	{
		Resource resource = new ClassPathResource("/vcap2.json");
		String content;
		try
		{
			content = new String(Files.readAllBytes(Paths.get(resource.getURI())));
			VcapResult result = parser.parse(content); 
			VcapPojo[] pojos = result.getPojos();
			assertNotNull(pojos);
			assertEquals(1, pojos.length);
			VcapPojo pojo = pojos[0];
			
			LOG.debug("pojo = " + pojo);
			assertEquals(4, pojo.getCredentials().size());
			assertEquals(0, pojo.getTags().length);
			assertEquals(0, pojo.getVolumeMounts().length);
			assertEquals("azure-documentdb", pojo.getLabel());
			assertNull(pojo.getProvider());
			assertEquals("azure-documentdb", pojo.getServiceBrokerName());
			assertEquals("mydocumentdb", pojo.getServiceInstanceName());
			assertEquals("standard", pojo.getServicePlan());
			assertNull(pojo.getSyslogDrainUrl());
			
			assertEquals("docdb123mj", pojo.getCredentials().get("documentdb_database_id"));
			assertEquals("dbs/ZFxCAA==/", pojo.getCredentials().get("documentdb_database_link"));
			assertEquals("https://hostname:443/", pojo.getCredentials().get("documentdb_host_endpoint"));
			assertEquals("3becR7JFnWamMvGwWYWWTV4WpeNhN8tOzJ74yjAxPKDpx65q2lYz60jt8WXU6HrIKrAIwhs0Hglf0123456789==", pojo.getCredentials().get("documentdb_master_key"));
		} 
		catch (IOException e)
		{
			LOG.error("Error reading json file", e);
		}
	}

	@Test
	public void testVcapTwoServicesWithNulls()
	{
		Resource resource = new ClassPathResource("/vcap3.json");
		String content;
		try
		{
			content = new String(Files.readAllBytes(Paths.get(resource.getURI())));
			VcapResult result = parser.parse(content); 
			VcapPojo[] pojos = result.getPojos();
			assertNotNull(pojos);
			assertEquals(2, pojos.length);
			VcapPojo pojo = result.findPojoForServiceType(VcapServiceType.AZURE_REDISCACHE);
			assertNotNull(pojo);
			
			LOG.debug("pojo = " + pojo);
			assertEquals(6, pojo.getCredentials().size());
			assertEquals(0, pojo.getTags().length);
			assertEquals(0, pojo.getVolumeMounts().length);
			assertEquals("azure-rediscache", pojo.getLabel());
			assertNull(pojo.getProvider());
			assertEquals("azure-rediscache", pojo.getServiceBrokerName());
			assertEquals("myredis", pojo.getServiceInstanceName());
			assertEquals("basic", pojo.getServicePlan());
			assertNull(pojo.getSyslogDrainUrl());
			assertEquals("hostname", pojo.getCredentials().get("hostname"));
			assertEquals("username", pojo.getCredentials().get("name"));
			assertEquals("6379", pojo.getCredentials().get("port"));
			assertEquals("DIK5TNeN6qFhrF6ATpxLLIZ7SpNMDg9xU+0123456789", pojo.getCredentials().get("primaryKey"));
			assertEquals("nP/eeEyCo2c11UP1g4D5KgZgQN8bfR6clM0123456789=", pojo.getCredentials().get("secondaryKey"));
			assertEquals("6380", pojo.getCredentials().get("sslPort"));

			pojo = result.findPojoForServiceType(VcapServiceType.AZURE_STORAGE);
			assertNotNull(pojo);
			
			LOG.debug("pojo = " + pojo);
			assertEquals(3, pojo.getCredentials().size());
			assertEquals(2, pojo.getTags().length);
			assertEquals(0, pojo.getVolumeMounts().length);
			assertEquals("azure-storage", pojo.getLabel());
			assertNull(pojo.getProvider());
			assertEquals("azure-storage", pojo.getServiceBrokerName());
			assertEquals("mystorage", pojo.getServiceInstanceName());
			assertEquals("standard", pojo.getServicePlan());
			assertNull(pojo.getSyslogDrainUrl());
			assertEquals("Azure", pojo.getTags()[0]);
			assertEquals("Storage", pojo.getTags()[1]);
			assertEquals("Vrpr5RGe7lf9FgNNmsrzYY9N8PUB1USAJLbDE2oytjD+3bUJXjt0H6Uwu+wGaQ+icyakQLNI8g8t0123456789==", pojo.getCredentials().get("primary_access_key"));
			assertEquals("7Dp5E253piI0rsfx1EIsSpw2ZQaJVe5uq56+OyEnom3wgC7DQs2jVbEwIL+90a7+u5xdLOzxxy0d0123456789==", pojo.getCredentials().get("secondary_access_key"));
			assertEquals("storage", pojo.getCredentials().get("storage_account_name"));
		} 
		catch (IOException e)
		{
			LOG.error("Error reading json file", e);
		}
	}

	@Test
	public void testVcapTwoServicesSameServiceType()
	{
		Resource resource = new ClassPathResource("/vcap4.json");
		String content;
		try
		{
			content = new String(Files.readAllBytes(Paths.get(resource.getURI())));
			VcapResult result = parser.parse(content); 
			VcapPojo[] pojos = result.getPojos();
			assertNotNull(pojos);
			assertEquals(2, pojos.length);
			int matches = result.findCountByServiceType(VcapServiceType.AZURE_STORAGE);
			assertEquals(2, matches);
			
			VcapPojo pojo = result.findPojoForServiceTypeAndServiceInstanceName(VcapServiceType.AZURE_STORAGE, "mystorage");
			LOG.debug("pojo = " + pojo);
			assertNotNull(pojo);
			
			pojo = result.findPojoForServiceTypeAndServiceInstanceName(VcapServiceType.AZURE_STORAGE, "mystorage2");
			LOG.debug("pojo = " + pojo);
			assertNotNull(pojo);
		} 
		catch (IOException e)
		{
			LOG.error("Error reading json file", e);
		}
	}

}
