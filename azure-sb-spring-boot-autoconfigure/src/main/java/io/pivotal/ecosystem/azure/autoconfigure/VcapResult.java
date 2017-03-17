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

public class VcapResult
{

	private VcapPojo[] pojos;
	
	public VcapPojo[] getPojos()
	{
		return pojos;
	}

	public void setPojos(VcapPojo[] pojos)
	{
		this.pojos = pojos;
	}

	public int findCountByServiceType(VcapServiceType serviceType)
	{
		int result = 0;
		
		if (serviceType != null)
		{
			for (int i=0; i<pojos.length; i++)
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
	
	public VcapPojo findByServiceType(VcapServiceType serviceType)
	{
		VcapPojo result = null;
		
		if (serviceType != null)
		{
			for (int i=0; i<pojos.length; i++)
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
	
	public VcapPojo findByServiceTypeAndServiceInstanceName(VcapServiceType serviceType, String serviceInstanceName)
	{
		VcapPojo result = null;
		
		if (serviceType != null && serviceInstanceName != null)
		{
			for (int i=0; i<pojos.length; i++)
			{
				VcapPojo pojo = pojos[i];
				if (serviceType.toString().equals(pojo.getServiceBrokerName()) &&
					serviceInstanceName.equals(pojo.getServiceInstanceName()))
				{
					result = pojo;
					break;
				}
			}
		}
		
		return result;
	}
}
