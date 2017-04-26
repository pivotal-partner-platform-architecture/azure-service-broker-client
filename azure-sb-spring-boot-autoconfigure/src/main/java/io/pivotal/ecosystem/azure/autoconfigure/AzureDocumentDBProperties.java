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

/**
 * 
 * @author pbopardikar This class will be instantiated by Spring Boot. The
 *         attributes defined in this class will get their values from the Cloud
 *         Foundry DocumentDB service instance. The name of the CF Service
 *         Instance and the DocumentDB Resource ID is defined in the
 *         azure-sb-documentdb-client project's application.properties
 */
@ConfigurationProperties(Constants.NAMESPACE_DOCUMENTDB)
public class AzureDocumentDBProperties extends BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureDocumentDBProperties.class);
	
	private String hostEndpoint;
	private String masterKey;

	private String resourceId = "myresource";
	private String databaseId = "mydatabaseId";
	private String link;

	public String getResourceId()
	{
		return resourceId;
	}

	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}

	public String getHostEndpoint()
	{
		return hostEndpoint;
	}

	public void setHostEndpoint(String hostEndpoint)
	{
		this.hostEndpoint = hostEndpoint;
	}

	public String getMasterKey()
	{
		return masterKey;
	}

	public void setMasterKey(String masterKey)
	{
		this.masterKey = masterKey;
	}

	public String getDatabaseId()
	{
		return databaseId;
	}

	public void setDatabaseId(String databaseId)
	{
		this.databaseId = databaseId;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public void populateDocumentDBPropertiesForServiceInstance(String serviceInstanceName) throws ServiceInstanceNotFoundException
	{
		LOG.debug("populateDocumentDBPropertiesForServiceInstance " + serviceInstanceName);
		VcapServiceType serviceType = VcapServiceType.AZURE_DOCUMENTDB;
		VcapPojo pojo = getResult().findPojoForServiceTypeAndServiceInstanceName(serviceType, serviceInstanceName);
		if (pojo != null)
		{
			setHostEndpoint(pojo.getCredentials().get(Constants.HOST_ENDPOINT));
			setMasterKey(pojo.getCredentials().get(Constants.MASTER_KEY));
			LOG.debug("populateDocumentDBPropertiesForServiceInstance updated property values. " + getDatabaseId() + ", " + getHostEndpoint() + ", " + getMasterKey());
		}
		else
		{
			String msg = "populateDocumentDBPropertiesForServiceInstance: No pojo found for serviceType " + serviceType + ", serviceInstanceName " + serviceInstanceName;
			LOG.error(msg);
			throw new ServiceInstanceNotFoundException(msg, serviceType, serviceInstanceName);
		}
	}

	@Override
	public String toString()
	{
		return "AzureDocumentDBProperties [resourceId=" + resourceId
				+ ", hostEndpoint=" + hostEndpoint + ", masterKey=" + masterKey + ", databaseId=" + databaseId + ", link=" + link + "]";
	}
}
