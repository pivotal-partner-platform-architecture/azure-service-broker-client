package io.pivotal.azuresb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class CloudConfig extends AbstractCloudConfig
{
	@Autowired
	private AzureRedisProperties properties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory()
	{
		JedisConnectionFactory cf = new JedisConnectionFactory();
		cf.setHostName(properties.getHostname());
		cf.setPort(Integer.valueOf(properties.getSslPort()));
		cf.setPassword(properties.getPrimaryKey());
		cf.setUseSsl(true);
		cf.afterPropertiesSet();
		return cf;
	}
}
