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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Constants.NAMESPACE_STORAGE)
public class AzureStorageProperties extends BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureStorageProperties.class);

	private String name;
	private String key;

	public String getName()
	{
		return name;
	}

	public void setName(String accountName)
	{
		this.name = accountName;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String accountKey)
	{
		this.key = accountKey;
	}

	public void populateStoragePropertiesForServiceInstance(String serviceInstanceName) throws ServiceInstanceNotFoundException
	{
		LOG.debug("populateStoragePropertiesForServiceInstance " + serviceInstanceName);
		VcapServiceType serviceType = VcapServiceType.AZURE_STORAGE;
		VcapPojo pojo = getResult().findPojoForServiceTypeAndServiceInstanceName(serviceType, serviceInstanceName);
		if (pojo != null)
		{
			setName(pojo.getCredentials().get(Constants.STORAGE_ACCOUNT_NAME));
			setKey(pojo.getCredentials().get(Constants.PRIMARY_ACCESS_KEY));
			LOG.debug("populateStoragePropertiesForServiceInstance updated property values. " + getName() + ", " + getKey());
		}
		else
		{
			String msg = "populateStoragePropertiesForServiceInstance: No pojo found for serviceType " + serviceType + ", serviceInstanceName " + serviceInstanceName;
			LOG.error(msg);
			throw new ServiceInstanceNotFoundException(msg, serviceType, serviceInstanceName);
		}
	}

	public String buildStorageConnectString()
	{
		String storageConnectionString = "DefaultEndpointsProtocol=http;" + "AccountName=" + getName() + ";" + "AccountKey=" + getKey();
		LOG.debug("storageConnectionString = " + storageConnectionString);
		return storageConnectionString;
	}

	@Override
	public String toString()
	{
		return "AzureStorageProperties [name=" + name + ", key=" + key + "]";
	}
}
