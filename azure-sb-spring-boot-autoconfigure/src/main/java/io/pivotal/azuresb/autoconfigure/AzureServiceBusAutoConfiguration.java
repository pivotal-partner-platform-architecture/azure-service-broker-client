package io.pivotal.azuresb.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;

@Configuration
@ConditionalOnMissingBean(ServiceBusContract.class)
public class AzureServiceBusAutoConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureServiceBusAutoConfiguration.class);
	private static final String TBD = "TBD";

	@Autowired
	private AzureSbServiceBusProperties properties;
	
	@Bean
	public ServiceBusContract serviceBusContract()
	{
		LOG.debug("serviceBusContract start...");
		ServiceBusContract service = null;
		
		if (! TBD.equals(properties.getNamespaceName()))
		{
			String profile = null;
			com.microsoft.windowsazure.Configuration config = new com.microsoft.windowsazure.Configuration();
			String connectionString = properties.buildServiceBusConnectString();
			ServiceBusConfiguration.configureWithConnectionString(profile, config, connectionString);
			service = ServiceBusService.create(config);
		}
		return service;
	}
}
