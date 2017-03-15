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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@ConditionalOnMissingBean(RedisConnectionFactory.class)
@EnableConfigurationProperties(AzureRedisProperties.class)
public class AzureRedisAutoConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureRedisAutoConfiguration.class);
	private static final String TBD = "TBD";

	public final AzureRedisProperties properties;

	public AzureRedisAutoConfiguration(AzureRedisProperties properties) {
		LOG.info("Constructor...");
		this.properties = properties;
	}

	@Bean
	@Profile("!testing")
	public RedisConnectionFactory redisConnectionFactory() {
		LOG.info("Hostname = " + properties.getHostname());
		JedisConnectionFactory cf = null;
		if (! TBD.equals(properties.getHostname()))
		{
			LOG.info("Creating JedisConnectionFactory...");
			cf = new JedisConnectionFactory();
			cf.setHostName(properties.getHostname());
			cf.setPort(Integer.valueOf(properties.getSslPort()));
			cf.setPassword(properties.getPrimaryKey());
			cf.setUseSsl(true);
			cf.afterPropertiesSet();
			LOG.info("Created JedisConnectionFactory...");
		}
		return cf;
	}

}
