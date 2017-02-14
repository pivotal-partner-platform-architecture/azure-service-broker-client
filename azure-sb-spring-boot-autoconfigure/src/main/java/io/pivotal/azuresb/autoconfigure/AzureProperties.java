package io.pivotal.azuresb.autoconfigure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureProperties.class);

	protected static final String VCAP_SERVICES = "VCAP_SERVICES";
	private static final String CREDENTIALS = "credentials";

	@Autowired
	private Environment environment;

	protected abstract void populateCallback(JSONObject credentials);
	
	protected void populate(String serviceName)
	{
		LOG.info("populate started...");
		String vcapServices = environment.getProperty(VCAP_SERVICES);
		if (vcapServices != null)
		{
			LOG.debug("vcapServices = " + vcapServices);
			try
			{
				JSONObject json = new JSONObject(vcapServices);
				JSONArray azureService = null;
				
				try
				{
					azureService = json.getJSONArray(serviceName);
				}
				catch (JSONException e)
				{
					LOG.debug("vcapServices does not contain " + serviceName);
				}
				
				if (azureService != null)
				{
					int numElements = azureService.length();
					LOG.debug("numElements = " + numElements);
					for (int i = 0; i < numElements; i++)
					{
						JSONObject service = azureService.getJSONObject(i);
						JSONObject creds = null;
						try
						{
							creds = service.getJSONObject(CREDENTIALS);
						}
						catch (JSONException e)
						{
							LOG.error("Found " + serviceName + ", but missing " + CREDENTIALS + " : " + VCAP_SERVICES);
						}
						if (creds != null)
						{
							populateCallback(creds);
						}
					}
				}
			} catch (JSONException e)
			{
				LOG.error("Error parsing " + VCAP_SERVICES, e);
			}
		}
	}
}
