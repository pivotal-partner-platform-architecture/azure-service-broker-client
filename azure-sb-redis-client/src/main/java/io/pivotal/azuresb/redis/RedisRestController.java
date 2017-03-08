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

package io.pivotal.azuresb.redis;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisRestController
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisRestController.class);
	private static final String CR = "</BR>";

	private final StringRedisTemplate template;

	public RedisRestController(StringRedisTemplate template) {
		this.template = template;
	}

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

