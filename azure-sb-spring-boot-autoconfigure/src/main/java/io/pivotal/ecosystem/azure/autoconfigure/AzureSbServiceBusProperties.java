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

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.servicebus")
public class AzureSbServiceBusProperties extends AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureSbServiceBusProperties.class);

	private static final String AZURE_SERVICEBUS = "azure-servicebus";
	private static final String SHARED_ACCESS_NAME = "shared_access_key_name";
	private static final String SHARED_ACCESS_KEY_VALUE = "shared_access_key_value";
	private static final String NAMESPACE_NAME = "namespace_name";
	private static final String AZURE_SERVICE_BUS_DOMAIN = "servicebus.windows.net";

	@Value("${namespace.name:TBD}")
	private String namespaceName;

	@Value("${shared.access.name:TBD}")
	private String sharedAccessName;

	@Value("${shared.access.key.value:TBD}")
	private String sharedAccessKeyValue;

	@PostConstruct
	private void populateProperties()
	{
		super.populate(AZURE_SERVICEBUS);
	}

	@Override
	protected void populateCallback(JSONObject creds)
	{
		try
		{
			namespaceName = creds.getString(NAMESPACE_NAME);
			sharedAccessName = creds.getString(SHARED_ACCESS_NAME);
			sharedAccessKeyValue = creds.getString(SHARED_ACCESS_KEY_VALUE);
		} catch (JSONException e)
		{
			LOG.error("Error parsing credentials for " + VCAP_SERVICES, e);
		}
	}

	public String buildServiceBusConnectString()
	{
		LOG.debug("namespace name = " + getNamespaceName());
		LOG.debug("shared access name = " + getSharedAccessName());
		LOG.debug("shared access key value = " + getSharedAccessKeyValue());
		String connectionString = 
				  "Endpoint=sb://" + getNamespaceName() + "." + AZURE_SERVICE_BUS_DOMAIN + "/;" 
				+ "SharedAccessKeyName=" + getSharedAccessName() + ";" 
				+ "SharedAccessKey=" + getSharedAccessKeyValue();
		return connectionString;
	}

	public String getNamespaceName()
	{
		return namespaceName;
	}

	public void setNamespaceName(String nameSpace)
	{
		this.namespaceName = nameSpace;
	}

	public String getSharedAccessName()
	{
		return sharedAccessName;
	}

	public void setSharedAccessName(String sharedAccessName)
	{
		this.sharedAccessName = sharedAccessName;
	}

	public String getSharedAccessKeyValue()
	{
		return sharedAccessKeyValue;
	}

	public void setSharedAccessKeyValue(String sharedAccessKeyValue)
	{
		this.sharedAccessKeyValue = sharedAccessKeyValue;
	}

}
