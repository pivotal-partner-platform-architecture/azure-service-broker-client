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
