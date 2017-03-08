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

package io.pivotal.azuresb.autoconfigure;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.CloudStorageAccount;

@Configuration
@ConditionalOnMissingBean(CloudStorageAccount.class)
@EnableConfigurationProperties(AzureStorageProperties.class)
public class AzureStorageAutoConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureStorageAutoConfiguration.class);
	private static final String TBD = "TBD";

	private final AzureStorageProperties properties;

	public AzureStorageAutoConfiguration(AzureStorageProperties properties) {
		this.properties = properties;
	}

	@Bean
	public CloudStorageAccount cloudStorageAccount()
	{
		CloudStorageAccount account = null;
		if (! TBD.equals(properties.getAccountName()))
		{
			try
			{
				account = CloudStorageAccount.parse(properties.buildStorageConnectString());
			} catch (InvalidKeyException e)
			{
				LOG.error("Error processing request ", e);
			} catch (URISyntaxException e)
			{
				LOG.error("Error processing request ", e);
			}
		}
		return account;
	}
}
