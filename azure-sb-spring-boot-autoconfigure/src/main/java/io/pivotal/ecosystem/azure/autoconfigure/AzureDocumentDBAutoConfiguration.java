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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;

/**
 * 
 * @author pbopardikar This class instantiates the DocumentClient bean that will
 *         be injected in the azure-sb-documentdb-client project
 */
@Configuration
@ConditionalOnMissingBean(DocumentClient.class)
@EnableConfigurationProperties(AzureDocumentDBProperties.class)
public class AzureDocumentDBAutoConfiguration 
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureDocumentDBAutoConfiguration.class);

	private final AzureDocumentDBProperties properties;

	@Autowired
	public AzureDocumentDBAutoConfiguration(AzureDocumentDBProperties properties) {
		this.properties = properties;
	}

	@Bean
	@Profile("!testing")
	public DocumentClient documentClient() {
		String hostname = properties.getHostEndpoint();
		String masterkey = properties.getMasterKey();
		LOG.debug("hostname = " + hostname);
		LOG.debug("masterkey = " + masterkey);
		return new DocumentClient(hostname, masterkey,
				ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
	}

}
