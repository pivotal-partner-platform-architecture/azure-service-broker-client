package io.pivotal.azuresb.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class AzureServiceBrokerAutoConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger(AzureServiceBrokerAutoConfiguration.class);
	private static final String TBD = "TBD";

	@Autowired
	private AzureRedisProperties properties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory()
	{
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
	
	public AzureServiceBrokerAutoConfiguration()
	{
		LOG.info("Constructor...");
	}}
