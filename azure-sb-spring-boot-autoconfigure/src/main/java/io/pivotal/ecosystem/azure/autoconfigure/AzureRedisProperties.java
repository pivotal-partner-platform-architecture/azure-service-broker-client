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

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.redis")
public class AzureRedisProperties extends AzureProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureRedisProperties.class);

	private static final String AZURE_REDISCACHE = "azure-rediscache";
	private static final String HOST_NAME = "hostname";
	private static final String SSL_PORT = "sslPort";
	private static final String PRIMARY_KEY = "primaryKey";

	@Value("${host.name:TBD}")
	private String hostname;

	@Value("${ssl.port:TBD}")
	private String sslPort;

	@Value("${primary.key:TBD}")
	private String primaryKey;

	@PostConstruct
	private void populateProperties()
	{
		super.populate(AZURE_REDISCACHE);
	}

	@Override
	protected void populateCallback(JSONObject creds)
	{
		try
		{
			hostname = creds.getString(HOST_NAME);
			sslPort = creds.getString(SSL_PORT);
			primaryKey = creds.getString(PRIMARY_KEY);
		} catch (JSONException e)
		{
			LOG.error("Error parsing credentials for " + VCAP_SERVICES, e);
		}
	}

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

}
