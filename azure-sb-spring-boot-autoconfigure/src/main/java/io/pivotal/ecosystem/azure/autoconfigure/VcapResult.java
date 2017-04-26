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

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class VcapResult
{
	private static final boolean LOGFLAG = true;
	private static final String PROPERTY_SOURCE_NAME = "defaultProperties";
	private static final String KEY = "key";
	private static final String NAME = "name";
	private static final String RESULT = "result";
	private static final String NAMESPACE_NAME = "namespaceName";
	private static final String SHARED_ACCESS_NAME = "sharedAccessName";
	private static final String SHARED_ACCESS_KEY_VALUE = "sharedAccessKeyValue";
	private static final String HOSTNAME = "hostname";
	private static final String SSL_PORT = "sslPort";
	private static final String PRIMARY_KEY = "primaryKey";
	private static final String HOST_ENDPOINT = "hostEndpoint";
	private static final String MASTER_KEY = "masterKey";

	private ConfigurableEnvironment confEnv;
	private VcapPojo[] pojos;

	/**
	 * Populates default properties during @EnvironmentPostProcessor processing.
	 * 
	 * Note that this class gets invoked before Spring creates the logging
	 * subsystem, so we just use System.out.println instead.
	 */
	public void populateProperties()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		populateDefaultStorageProperties(map, findPojoForServiceType(VcapServiceType.AZURE_STORAGE));
		populateDefaultServiceBusProperties(map, findPojoForServiceType(VcapServiceType.AZURE_SERVICEBUS));
		populateDefaultRedisProperties(map, findPojoForServiceType(VcapServiceType.AZURE_REDISCACHE));
		populateDefaultDocumentDBProperties(map, findPojoForServiceType(VcapServiceType.AZURE_DOCUMENTDB));
		addOrReplace(confEnv.getPropertySources(), map);
	}

	public VcapPojo findPojoForServiceTypeAndServiceInstanceName(VcapServiceType serviceType, String serviceInstanceName)
	{
		log("VcapResult.findPojoForServiceTypeAndServiceInstanceName " + serviceType + ", " + serviceInstanceName);
		VcapPojo result = null;

		if (serviceType != null && serviceInstanceName != null)
		{
			for (int i = 0; i < pojos.length; i++)
			{
				VcapPojo pojo = pojos[i];
				if (serviceType.toString().equals(pojo.getServiceBrokerName()) && serviceInstanceName.equals(pojo.getServiceInstanceName()))
				{
					result = pojo;
					break;
				}
			}
		}
		log("VcapResult.findPojoForServiceTypeAndServiceInstanceName result = " + result);

		return result;
	}

	public VcapPojo[] getPojos()
	{
		return pojos;
	}

	public void setPojos(VcapPojo[] pojos)
	{
		this.pojos = pojos;
	}

	public ConfigurableEnvironment getConfEnv()
	{
		return confEnv;
	}

	public void setConfEnv(ConfigurableEnvironment confEnv)
	{
		this.confEnv = confEnv;
	}

	VcapPojo findPojoForServiceType(VcapServiceType serviceType)
	{
		VcapPojo pojo = null;

		switch (findCountByServiceType(serviceType))
		{
		case 0:
			log("VcapResult.findPojoForServiceType: No services of type " + serviceType.toString() + " found.");
			break;
		case 1:
			log("VcapResult.findPojoForServiceType: One services of type " + serviceType.toString() + " found.");
			pojo = findByServiceType(serviceType);
			if (pojo != null)
			{
				log("VcapResult.findPojoForServiceType: Found the matching pojo");
			}
			break;
		default:
			log("VcapResult.findPojoForServiceType: More than one service of type " + serviceType.toString()
					+ " found, cannot autoconfigure service, must use factory instead.");
			break;
		}
		return pojo;
	}

	int findCountByServiceType(VcapServiceType serviceType)
	{
		int result = 0;

		if (serviceType != null)
		{
			for (int i = 0; i < pojos.length; i++)
			{
				VcapPojo pojo = pojos[i];
				if (serviceType.toString().equals(pojo.getServiceBrokerName()))
				{
					result++;
				}
			}
		}

		return result;
	}

	private void populateDefaultStorageProperties(Map<String, Object> map, VcapPojo pojo)
	{
		log("VcapResult.populateDefaultStorageProperties " + pojo);
		map.put(Constants.NAMESPACE_STORAGE + "." + RESULT, this);
		if (pojo != null)
		{
			map.put(Constants.NAMESPACE_STORAGE + "." + NAME, pojo.getCredentials().get(Constants.STORAGE_ACCOUNT_NAME));
			map.put(Constants.NAMESPACE_STORAGE + "." + KEY, pojo.getCredentials().get(Constants.PRIMARY_ACCESS_KEY));
			log("VcapResult.populateDefaultStorageProperties: Updated Storage properties");
		}
	}

	private void populateDefaultServiceBusProperties(Map<String, Object> map, VcapPojo pojo)
	{
		log("VcapResult.populateDefaultServiceBusProperties " + pojo);
		map.put(Constants.NAMESPACE_SERVICE_BUS + "." + RESULT, this);
		if (pojo != null)
		{
			map.put(Constants.NAMESPACE_SERVICE_BUS + "." + NAMESPACE_NAME, pojo.getCredentials().get(Constants.NAMESPACE_NAME));
			map.put(Constants.NAMESPACE_SERVICE_BUS + "." + SHARED_ACCESS_NAME, pojo.getCredentials().get(Constants.SHARED_ACCESS_NAME));
			map.put(Constants.NAMESPACE_SERVICE_BUS + "." + SHARED_ACCESS_KEY_VALUE, pojo.getCredentials().get(Constants.SHARED_ACCESS_KEY_VALUE));
			log("VcapResult.populateDefaultServiceBusProperties: Updated Service Bus properties");
		}
	}

	private void populateDefaultRedisProperties(Map<String, Object> map, VcapPojo pojo)
	{
		log("VcapResult.populateDefaultRedisProperties " + pojo);
		map.put(Constants.NAMESPACE_REDIS + "." + RESULT, this);
		if (pojo != null)
		{
			map.put(Constants.NAMESPACE_REDIS + "." + HOSTNAME, pojo.getCredentials().get(Constants.HOST_NAME));
			map.put(Constants.NAMESPACE_REDIS + "." + SSL_PORT, pojo.getCredentials().get(Constants.SSL_PORT));
			map.put(Constants.NAMESPACE_REDIS + "." + PRIMARY_KEY, pojo.getCredentials().get(Constants.PRIMARY_KEY));
			log("VcapResult.populateDefaultRedisProperties: Updated Redis properties");
		}
	}

	private void populateDefaultDocumentDBProperties(Map<String, Object> map, VcapPojo pojo)
	{
		log("VcapResult.populateDefaultDocumentDBProperties " + pojo);
		map.put(Constants.NAMESPACE_DOCUMENTDB + "." + RESULT, this);
		if (pojo != null)
		{
			map.put(Constants.NAMESPACE_DOCUMENTDB + "." + HOST_ENDPOINT, pojo.getCredentials().get(Constants.HOST_ENDPOINT));
			map.put(Constants.NAMESPACE_DOCUMENTDB + "." + MASTER_KEY, pojo.getCredentials().get(Constants.MASTER_KEY));
			log("VcapResult.populateDefaultDocumentDBProperties: Updated Service Bus properties");
		}
	}

	private VcapPojo findByServiceType(VcapServiceType serviceType)
	{
		VcapPojo result = null;

		if (serviceType != null)
		{
			for (int i = 0; i < pojos.length; i++)
			{
				VcapPojo pojo = pojos[i];
				if (serviceType.toString().equals(pojo.getServiceBrokerName()))
				{
					result = pojo;
					break;
				}
			}
		}

		return result;
	}

	private void addOrReplace(MutablePropertySources propertySources, Map<String, Object> map)
	{
		MapPropertySource target = null;
		if (propertySources.contains(PROPERTY_SOURCE_NAME))
		{
			PropertySource<?> source = propertySources.get(PROPERTY_SOURCE_NAME);
			if (source instanceof MapPropertySource)
			{
				target = (MapPropertySource) source;
				for (String key : map.keySet())
				{
					if (!target.containsProperty(key))
					{
						target.getSource().put(key, map.get(key));
					}
				}
			}
		}
		if (target == null)
		{
			target = new MapPropertySource(PROPERTY_SOURCE_NAME, map);
		}
		if (!propertySources.contains(PROPERTY_SOURCE_NAME))
		{
			propertySources.addLast(target);
		}
	}

	private void log(String msg)
	{
		if (LOGFLAG)
		{
			System.out.println(msg);
		}
	}

}
