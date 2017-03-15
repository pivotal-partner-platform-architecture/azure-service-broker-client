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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

@ActiveProfiles( profiles = "testing" )
public class ConfigurationTest
{
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationTest.class);

	@Autowired
	private AzureStorageProperties storageProperties;
	
	@Test
	public void test()
	{
		assertNotNull(storageProperties);
		LOG.debug("************************************************************");
		LOG.debug("");
		LOG.debug(storageProperties.toString());
		LOG.debug("");
		LOG.debug("************************************************************");
		assertEquals("TestingAccount", storageProperties.getName());
		assertEquals("TestingKey", storageProperties.getKey());
	}

}
