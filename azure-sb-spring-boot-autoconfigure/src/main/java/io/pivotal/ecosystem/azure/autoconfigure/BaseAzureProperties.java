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
import org.springframework.core.env.Environment;

abstract public class BaseAzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(BaseAzureProperties.class);

	private Environment environment;
	private VcapParser parser;
	private VcapResult result;

	public BaseAzureProperties(Environment environment, VcapParser parser)
	{
		this.environment = environment;
		this.parser = parser;
	}
	
	abstract protected void populateProperties(VcapPojo pojo); 
	
	protected void populateProperties(VcapServiceType serviceType)
	{
		String vcapServices = environment.getProperty(VcapParser.VCAP_SERVICES);
		result = parser.parse(vcapServices); 
		
		switch  (result.findCountByServiceType(serviceType))
		{
		case 0:
			LOG.info("No services of type " + serviceType.toString() + " found.");
			break;
		case 1:
			LOG.info("One services of type " + serviceType.toString() + " found.");
			VcapPojo pojo = result.findByServiceType(serviceType);
			if (pojo != null)
			{
				LOG.debug("Found the matching pojo");
				populateProperties(pojo);
			}
			break;
		default:
			LOG.warn("More than one service of type " + serviceType.toString() + " found, cannot autoconfigure service, must use factory instead.");
			break;
		}
	}
	
	protected void populatePropertiesForServiceInstance(VcapServiceType serviceType, String serviceInstanceName)
	{
		VcapPojo pojo = result.findByServiceTypeAndServiceInstanceName(serviceType, serviceInstanceName);
		if (pojo != null)
		{
			LOG.debug("Found the matching pojo for service instance name " + serviceInstanceName);
			populateProperties(pojo);
		}
	}

}
