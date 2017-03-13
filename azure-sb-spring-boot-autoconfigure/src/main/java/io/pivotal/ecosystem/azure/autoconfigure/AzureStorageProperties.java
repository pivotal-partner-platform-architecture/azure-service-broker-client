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
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.storage.account")
public class AzureStorageProperties extends AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureStorageProperties.class);

	private static final String AZURE_STORAGE = "azure-storage";
	private static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
	private static final String PRIMARY_ACCESS_KEY = "primary_access_key";
	
	private String name = "TBD";
	private String key = "TBD";

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
			name = creds.getString(STORAGE_ACCOUNT_NAME);
			key = creds.getString(PRIMARY_ACCESS_KEY);
		} catch (JSONException e)
		{
			LOG.error("Error parsing credentials for " + VCAP_SERVICES, e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String accountName) {
		this.name = accountName;
	}

	public String getAccountKey() {
		return key;
	}

	public void setAccountKey(String accountKey) {
		this.key = accountKey;
	}
	
	public String buildStorageConnectString()
	{
		LOG.debug("storage account name = " + getName());
		LOG.debug("storage account key = " + getAccountKey());
		String storageConnectionString = "DefaultEndpointsProtocol=http;"
				+ "AccountName=" + getName() + ";"
				+ "AccountKey=" + getAccountKey();
	    return storageConnectionString;
	}
	
	@Override
	public String toString()
	{
		return "AzureStorageProperties [accountName=" + name + ", accountKey=" + key + "]";
	}



}
