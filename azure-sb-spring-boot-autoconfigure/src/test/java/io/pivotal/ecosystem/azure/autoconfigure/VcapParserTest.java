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

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

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
			VcapPojo[] pojos = parser.parse(content);
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
	
}
