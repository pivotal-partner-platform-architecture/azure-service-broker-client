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

package io.pivotal.ecosystem.azure.storage.queue;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

@RestController
public class QueueRestController
{
	private static final Logger LOG = LoggerFactory.getLogger(QueueRestController.class);
	private static final String CR = "</BR>";

	@Autowired
	private CloudStorageAccount account;

	@RequestMapping(value = "/queue", method = RequestMethod.GET)
	public String process(HttpServletResponse response)
	{
		LOG.info("QueueRestController process start...");
		StringBuffer result = new StringBuffer();

		try
		{
			result.append("Connecting to storage account..." + CR);
			result.append("Creating queue..." + CR);
			CloudQueueClient queueClient = account.createCloudQueueClient();
			CloudQueue queue = queueClient.getQueueReference("productqueue");
			queue.createIfNotExists();

			result.append("Adding message to queue..." + CR);
			CloudQueueMessage message = new CloudQueueMessage("A new product is available!");
			queue.addMessage(message);

			result.append("Retrieving message from queue..." + CR);
			CloudQueueMessage retrievedMessage = queue.retrieveMessage();
			if (retrievedMessage != null)
			{
				result.append("message = " + retrievedMessage.getMessageContentAsString() + CR);
				result.append("Deleting message from queue..." + CR);
				queue.deleteMessage(retrievedMessage);
			}

		} catch (Exception e)
		{
			LOG.error("Error processing request ", e);
		}

		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("QueueRestController process end");
		return result.toString();
	}

}
