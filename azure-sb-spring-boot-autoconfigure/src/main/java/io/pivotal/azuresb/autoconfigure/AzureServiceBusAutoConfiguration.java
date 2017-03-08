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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;

@Configuration
@ConditionalOnMissingBean(ServiceBusContract.class)
@EnableConfigurationProperties(AzureSbServiceBusProperties.class)
public class AzureServiceBusAutoConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureServiceBusAutoConfiguration.class);
	private static final String TBD = "TBD";

	private final AzureSbServiceBusProperties properties;

	public AzureServiceBusAutoConfiguration(AzureSbServiceBusProperties properties) {
		this.properties = properties;
	}

	@Bean
	public ServiceBusContract serviceBusContract()
	{
		LOG.debug("serviceBusContract start...");
		ServiceBusContract service = null;
		
		if (! TBD.equals(properties.getNamespaceName()))
		{
			com.microsoft.windowsazure.Configuration config = new com.microsoft.windowsazure.Configuration();
			String connectionString = properties.buildServiceBusConnectString();
			ServiceBusConfiguration.configureWithConnectionString(null, config, connectionString);
			service = ServiceBusService.create(config);
		}
		return service;
	}
}
