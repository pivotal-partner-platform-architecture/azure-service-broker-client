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

@ConfigurationProperties(Constants.NAMESPACE_SERVICE_BUS)
public class AzureServiceBusProperties extends BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureServiceBusProperties.class);

	private static final String AZURE_SERVICE_BUS_DOMAIN = "servicebus.windows.net";

	private String namespaceName;
	private String sharedAccessName;
	private String sharedAccessKeyValue;
	
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

	public void populateServiceBusPropertiesForServiceInstance(String serviceInstanceName) throws ServiceInstanceNotFoundException
	{
		LOG.debug("populateServiceBusPropertiesForServiceInstance " + serviceInstanceName);
		VcapServiceType serviceType = VcapServiceType.AZURE_SERVICEBUS;
		VcapPojo pojo = getResult().findPojoForServiceTypeAndServiceInstanceName(serviceType, serviceInstanceName);
		if (pojo != null)
		{
			setNamespaceName(pojo.getCredentials().get(Constants.NAMESPACE_NAME));
			setSharedAccessKeyValue(pojo.getCredentials().get(Constants.SHARED_ACCESS_KEY_VALUE));
			setSharedAccessName(pojo.getCredentials().get(Constants.SHARED_ACCESS_NAME));
			LOG.debug("populateServiceBusPropertiesForServiceInstance updated property values. " + getNamespaceName() + ", " + getSharedAccessKeyValue() + ", " + getSharedAccessName());
		}
		else
		{
			String msg = "populateServiceBusPropertiesForServiceInstance: No pojo found for serviceType " + serviceType + ", serviceInstanceName " + serviceInstanceName;
			LOG.error(msg);
			throw new ServiceInstanceNotFoundException(msg, serviceType, serviceInstanceName);
		}
	}
	
	public String buildServiceBusConnectString()
	{
		String connectionString = 
				  "Endpoint=sb://" + getNamespaceName() + "." + AZURE_SERVICE_BUS_DOMAIN + "/;" 
				+ "SharedAccessKeyName=" + getSharedAccessName() + ";" 
				+ "SharedAccessKey=" + getSharedAccessKeyValue();
		LOG.debug("connectionString name = " + connectionString);
		return connectionString;
	}


	@Override
	public String toString()
	{
		return "AzureSbServiceBusProperties [namespaceName=" + namespaceName
				+ ", sharedAccessName=" + sharedAccessName + ", sharedAccessKeyValue=" + sharedAccessKeyValue + "]";
	}

	
}
