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

@ConfigurationProperties(Constants.NAMESPACE_REDIS)
public class AzureRedisProperties extends BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureRedisProperties.class);

	private String hostname;
	private String sslPort;
	private String primaryKey;
	
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

	public void populateRedisPropertiesForServiceInstance(String serviceInstanceName) throws ServiceInstanceNotFoundException
	{
		LOG.debug("populateRedisPropertiesForServiceInstance " + serviceInstanceName);
		VcapServiceType serviceType = VcapServiceType.AZURE_REDISCACHE;
		VcapPojo pojo = getResult().findPojoForServiceTypeAndServiceInstanceName(serviceType, serviceInstanceName);
		if (pojo != null)
		{
			setHostname(pojo.getCredentials().get(Constants.HOST_NAME));
			setPrimaryKey(pojo.getCredentials().get(Constants.PRIMARY_KEY));
			setSslPort(pojo.getCredentials().get(Constants.SSL_PORT));
			LOG.debug("populateRedisPropertiesForServiceInstance updated property values. " + getHostname() + ", " + getPrimaryKey() + ", " + getSslPort());
		}
		else
		{
			String msg = "populateRedisPropertiesForServiceInstance: No pojo found for serviceType " + serviceType + ", serviceInstanceName " + serviceInstanceName;
			LOG.error(msg);
			throw new ServiceInstanceNotFoundException(msg, serviceType, serviceInstanceName);
		}
	}

	@Override
	public String toString()
	{
		return "AzureRedisProperties [hostname=" + hostname + ", sslPort="
				+ sslPort + ", primaryKey=" + primaryKey + "]";
	}

	
}
