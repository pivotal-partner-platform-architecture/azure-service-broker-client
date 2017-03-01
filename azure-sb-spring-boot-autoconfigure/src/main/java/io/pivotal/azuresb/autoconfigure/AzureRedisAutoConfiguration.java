package io.pivotal.azuresb.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
