package io.pivotal.azuresb.autoconfigure;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.redis")
public class AzureRedisProperties extends AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureRedisProperties.class);

	private static final String AZURE_REDISCACHE = "azure-rediscache";
	private static final String HOST_NAME = "hostname";
	private static final String SSL_PORT = "sslPort";
	private static final String PRIMARY_KEY = "primaryKey";

	@Value("${host.name:TBD}")
	private String hostname;

	@Value("${ssl.port:TBD}")
	private String sslPort;

	@Value("${primary.key:TBD}")
	private String primaryKey;

	@PostConstruct
	private void populateProperties()
	{
		super.populate(AZURE_REDISCACHE);
	}

	@Override
	protected void populateCallback(JSONObject creds)
	{
		try
		{
			hostname = creds.getString(HOST_NAME);
			sslPort = creds.getString(SSL_PORT);
			primaryKey = creds.getString(PRIMARY_KEY);
		} catch (JSONException e)
		{
			LOG.error("Error parsing credentials for " + VCAP_SERVICES, e);
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
