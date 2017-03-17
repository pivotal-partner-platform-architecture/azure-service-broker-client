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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@ConfigurationProperties("azure.servicebus")
public class AzureServiceBusProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureServiceBusProperties.class);

	private static final String AZURE_SERVICEBUS = "azure-servicebus";
	private static final String SHARED_ACCESS_NAME = "shared_access_key_name";
	private static final String SHARED_ACCESS_KEY_VALUE = "shared_access_key_value";
	private static final String NAMESPACE_NAME = "namespace_name";
	private static final String AZURE_SERVICE_BUS_DOMAIN = "servicebus.windows.net";

	@Autowired
	private VcapParser parser;

	@Autowired
	private Environment environment;

	private String namespaceName = "TBD";
	private String sharedAccessName = "TBD";
	private String sharedAccessKeyValue = "TBD";

	@PostConstruct
	private void populateProperties()
	{
		String vcapServices = environment.getProperty(VcapParser.VCAP_SERVICES);
		VcapResult result = parser.parse(vcapServices); 
		VcapPojo[] pojos = result.getPojos();

		for (int i=0; i<pojos.length; i++)
		{
			VcapPojo pojo = pojos[i];
			if (AZURE_SERVICEBUS.equals(pojo.getServiceBrokerName()))
			{
				LOG.debug("Found the service bus key");
				namespaceName = pojo.getCredentials().get(NAMESPACE_NAME);
				sharedAccessName = pojo.getCredentials().get(SHARED_ACCESS_NAME);
				sharedAccessKeyValue = pojo.getCredentials().get(SHARED_ACCESS_KEY_VALUE);
			}
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

	@Override
	public String toString()
	{
		return "AzureSbServiceBusProperties [parser=" + parser + ", environment=" + environment + ", namespaceName=" + namespaceName
				+ ", sharedAccessName=" + sharedAccessName + ", sharedAccessKeyValue=" + sharedAccessKeyValue + "]";
	}

	
}
