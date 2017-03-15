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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VcapPojo
{
	private String serviceBrokerName;
	private Map<String, String> credentials = new HashMap<String, String>();
	private String label;
	private String serviceInstanceName;
	private String servicePlan;
	private String provider;
	private String syslogDrainUrl;
	private String[] tags;
	private String[] volumeMounts;
	
	public String getServiceBrokerName()
	{
		return serviceBrokerName;
	}
	public void setServiceBrokerName(String serviceBrokerName)
	{
		this.serviceBrokerName = serviceBrokerName;
	}
	public Map<String, String> getCredentials()
	{
		return credentials;
	}
	public void setCredentials(Map<String, String> credentials)
	{
		this.credentials = credentials;
	}
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	public String getServiceInstanceName()
	{
		return serviceInstanceName;
	}
	public void setServiceInstanceName(String serviceName)
	{
		this.serviceInstanceName = serviceName;
	}
	public String getServicePlan()
	{
		return servicePlan;
	}
	public void setServicePlan(String servicePlan)
	{
		this.servicePlan = servicePlan;
	}
	public String getProvider()
	{
		return provider;
	}
	public void setProvider(String provider)
	{
		this.provider = provider;
	}
	public String getSyslogDrainUrl()
	{
		return syslogDrainUrl;
	}
	public void setSyslogDrainUrl(String syslogDrainUrl)
	{
		this.syslogDrainUrl = syslogDrainUrl;
	}
	public String[] getTags()
	{
		return tags;
	}
	public void setTags(String[] tags)
	{
		this.tags = tags;
	}
	public String[] getVolumeMounts()
	{
		return volumeMounts;
	}
	public void setVolumeMounts(String[] volumeMounts)
	{
		this.volumeMounts = volumeMounts;
	}
	@Override
	public String toString()
	{
		return "VcapPojo [serviceBrokerName=" + serviceBrokerName + ", credentials=" + credentials + ", label=" + label + ", serviceName="
				+ serviceInstanceName + ", servicePlan=" + servicePlan + ", provider=" + provider + ", syslogDrainUrl=" + syslogDrainUrl
				+ ", tags=" + Arrays.toString(tags) + ", volumeMounts=" + Arrays.toString(volumeMounts) + "]";
	}
	
	
	
 }
