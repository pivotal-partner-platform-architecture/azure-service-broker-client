package io.pivotal.azuresb.autoconfigure;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@ConfigurationProperties()
public class AzureRedisProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureRedisProperties.class);

	private static final String VCAP_SERVICES = "VCAP_SERVICES";
	private static final String AZURE_REDISCACHE = "azure-rediscache";
	private static final String CREDENTIALS = "credentials";
	private static final String HOST_NAME = "hostname";
	private static final String SSL_PORT = "sslPort";
	private static final String PRIMARY_KEY = "primaryKey";

	@Autowired
	private Environment environment;

	@Value("${host.name:TBD}")
	private String hostname;

	@Value("${ssl.port:TBD}")
	private String sslPort;

	@Value("${primary.key:TBD}")
	private String primaryKey;

	@PostConstruct
	private void populate()
	{
		LOG.info("AzureSbServiceBusProperties populate started...");
		String vcapServices = environment.getProperty(VCAP_SERVICES);
		if (vcapServices != null)
		{
			LOG.debug("vcapServices = " + vcapServices);
			try
			{
				JSONObject json = new JSONObject(vcapServices);
				JSONArray azureStorage = null;
				
				try
				{
					azureStorage = json.getJSONArray(AZURE_REDISCACHE);
				}
				catch (JSONException e)
				{
					LOG.debug("vcapServices does not contain " + AZURE_REDISCACHE);
				}
				
				if (azureStorage != null)
				{
					int numElements = azureStorage.length();
					LOG.debug("numElements = " + numElements);
					for (int i = 0; i < numElements; i++)
					{
						JSONObject storage = azureStorage.getJSONObject(i);
						JSONObject creds = null;
						try
						{
							creds = storage.getJSONObject(CREDENTIALS);
						}
						catch (JSONException e)
						{
							LOG.error("Found " + AZURE_REDISCACHE + ", but missing " + CREDENTIALS + " : " + VCAP_SERVICES);
						}
						if (creds != null)
						{
							hostname = creds.getString(HOST_NAME);
							sslPort = creds.getString(SSL_PORT);
							primaryKey = creds.getString(PRIMARY_KEY);
						}
					}
				}
			} catch (JSONException e)
			{
				LOG.error("Error parsing " + VCAP_SERVICES, e);
			}
		}
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public String getSslPort()
	{
		return sslPort;
	}

	public void setSslPort(String sslPort)
	{
		this.sslPort = sslPort;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey)
	{
		this.primaryKey = primaryKey;
	}
}
