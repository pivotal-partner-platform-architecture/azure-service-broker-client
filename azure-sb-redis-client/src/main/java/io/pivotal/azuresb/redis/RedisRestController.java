package io.pivotal.azuresb.redis;

import io.pivotal.azuresb.autoconfigure.AzureRedisProperties;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties(AzureRedisProperties.class)
public class RedisRestController
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisRestController.class);
	private static final String CR = "</BR>";

	@Autowired
	private AzureRedisProperties properties;
	
	@Autowired
	private StringRedisTemplate template;

	@RequestMapping(value = "/redis", method = RequestMethod.GET)
	public String process(HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();

		LOG.info("RedisRestController process start...");
		  
		String keyName = "product";
		BoundValueOperations<String, String> valueOps = template.boundValueOps(keyName);
		result.append("Setting product value..." + CR);
		valueOps.set("pcf");

		String value = valueOps.get();
		result.append("Getting product value = " + value + CR);
		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("RedisRestController process end");
		return result.toString();
	}

}

