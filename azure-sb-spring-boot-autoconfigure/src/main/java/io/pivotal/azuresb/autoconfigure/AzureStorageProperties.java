package io.pivotal.azuresb.autoconfigure;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties()
public class AzureStorageProperties extends AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureStorageProperties.class);

	private static final String AZURE_STORAGE = "azure-storage";
	private static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
	private static final String PRIMARY_ACCESS_KEY = "primary_access_key";
	
	@Value("${storage.account.name:TBD}") 
	private String storageAccountName;
	
	@Value("${storage.account.key:TBD}") 
	private String storageAccountKey;

	@PostConstruct
	private void populateProperties()
	{
		super.populate(AZURE_STORAGE);
	}

	@Override
	protected void populateCallback(JSONObject creds)
	{
		try
		{
			storageAccountName = creds.getString(STORAGE_ACCOUNT_NAME);
			storageAccountKey = creds.getString(PRIMARY_ACCESS_KEY);
		} catch (JSONException e)
		{
			LOG.error("Error parsing credentials for " + VCAP_SERVICES, e);
		}
	}

	public String getStorageAccountName() {
		return storageAccountName;
	}

	public void setStorageAccountName(String storageAccountName) {
		this.storageAccountName = storageAccountName;
	}

	public String getStorageAccountKey() {
		return storageAccountKey;
	}

	public void setStorageAccountKey(String storageAccountKey) {
		this.storageAccountKey = storageAccountKey;
	}
	
	public String buildStorageConnectString()
	{
		LOG.debug("storage account name = " + getStorageAccountName());
		LOG.debug("storage account key = " + getStorageAccountKey());
		String storageConnectionString = "DefaultEndpointsProtocol=http;"
				+ "AccountName=" + getStorageAccountName() + ";" 
				+ "AccountKey=" + getStorageAccountKey();
	    return storageConnectionString;
	}

}
