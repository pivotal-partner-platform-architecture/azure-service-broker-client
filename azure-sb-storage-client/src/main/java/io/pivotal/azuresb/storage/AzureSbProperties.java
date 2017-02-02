package io.pivotal.azuresb.storage;

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

@ConfigurationProperties("azuresb")
public class AzureSbProperties {
	
	private static final Logger LOG = LoggerFactory.getLogger(AzureSbProperties.class);

	private static final String VCAP_SERVICES = "VCAP_SERVICES";
	private static final String AZURE_STORAGE = "azure-storage";
	private static final String CREDENTIALS = "credentials";
	private static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
	private static final String PRIMARY_ACCESS_KEY = "primary_access_key";
	
	@Autowired
	private Environment environment;
	
	@Value("${storage.account.name:TBD}") 
	private String storageAccountName;
	
	@Value("${storage.account.key:TBD}") 
	private String storageAccountKey;

	@PostConstruct
	private void populate()
	{
		LOG.info("AzureSBProperties populate started...");
		String vcapServices = environment.getProperty(VCAP_SERVICES);
		if (vcapServices != null)
		{
			LOG.debug("vcapServices = " + vcapServices);
			try {
				JSONObject json = new JSONObject(vcapServices);
				JSONArray azureStorage = json.getJSONArray(AZURE_STORAGE);
				int numElements = azureStorage.length();
				LOG.debug("numElements = " + numElements);
				for (int i=0; i<numElements; i++)
				{
					JSONObject storage = azureStorage.getJSONObject(i);
					JSONObject creds = storage.getJSONObject(CREDENTIALS);
					storageAccountName = creds.getString(STORAGE_ACCOUNT_NAME);
					storageAccountKey = creds.getString(PRIMARY_ACCESS_KEY);
				}
			} catch (JSONException e) {
				LOG.error("Error parsing " + VCAP_SERVICES, e);
			}
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
