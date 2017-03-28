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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@ConfigurationProperties("azure.storage.account")
public class AzureStorageProperties extends BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureStorageProperties.class);

	private static final VcapServiceType SERVICE_TYPE = VcapServiceType.AZURE_STORAGE;
	private static final String TBD = "TBD";
	private static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
	private static final String PRIMARY_ACCESS_KEY = "primary_access_key";
	
	private String name = TBD;
	private String key = TBD;
	
	public AzureStorageProperties(Environment environment, VcapParser parser)
	{
		super(environment, parser);
	}

	@PostConstruct
	private void populateProperties()
	{
		populateProperties(SERVICE_TYPE);
	}

	public void populatePropertiesForServiceInstance(String serviceInstanceName)
	{
		populatePropertiesForServiceInstance(SERVICE_TYPE, serviceInstanceName);
	}

	@Override
	protected void populateProperties(VcapPojo pojo)
	{
		name = pojo.getCredentials().get(STORAGE_ACCOUNT_NAME);
		key = pojo.getCredentials().get(PRIMARY_ACCESS_KEY);
	}

	public String getName() {
		return name;
	}

	public void setName(String accountName) {
		this.name = accountName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String accountKey) {
		this.key = accountKey;
	}
	
	public String buildStorageConnectString()
	{
		String storageConnectionString = "DefaultEndpointsProtocol=http;"
				+ "AccountName=" + getName() + ";"
				+ "AccountKey=" + getKey();
		LOG.debug("storageConnectionString = " + storageConnectionString);
	    return storageConnectionString;
	}
	
	@Override
	public String toString()
	{
		return "AzureStorageProperties [accountName=" + name + ", accountKey=" + key + "]";
	}
}
