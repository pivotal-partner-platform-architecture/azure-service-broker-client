package io.pivotal.azuresb.bus;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.CreateQueueResult;
import com.microsoft.windowsazure.services.servicebus.models.ListQueuesResult;
import com.microsoft.windowsazure.services.servicebus.models.QueueInfo;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMode;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveQueueMessageResult;

@RestController
@EnableConfigurationProperties(AzureSbServiceBusProperties.class)
public class QueueRestController
{
	private static final Logger LOG = LoggerFactory.getLogger(QueueRestController.class);
	private static final String CR = "</BR>";
	private static final String QUEUE_NAME = "PRODUCT_QUEUE";

	@Autowired
	private AzureSbServiceBusProperties properties;

	@RequestMapping(value = "/queue", method = RequestMethod.GET)
	public String process(HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();

		LOG.info("QueueRestController process start...");

		result.append("Connecting to service bus..." + CR);
		String profile = null;
		Configuration config = new Configuration();
		String connectionString = properties.buildServiceBusConnectString();
		ServiceBusConfiguration.configureWithConnectionString(profile, config, connectionString);

		result.append("Checking for queue " + QUEUE_NAME + CR);
		ServiceBusContract service = ServiceBusService.create(config);
		
		boolean queueExists = false;
		ListQueuesResult queueList;
		try
		{
			queueList = service.listQueues();
			for (QueueInfo queue:queueList.getItems())
			{
				result.append("Found queue " + queue.getPath() + CR);
				
				// Looks like Azure uses lower case for queue names so we need to ignore case
				if (QUEUE_NAME.equalsIgnoreCase(queue.getPath()))
				{
					queueExists = true;
					break;
				}
			}
			
			QueueInfo queueInfo = new QueueInfo(QUEUE_NAME);
			if (! queueExists)
			{
				try
				{
					result.append("Creating queue " + QUEUE_NAME + CR);
					CreateQueueResult queueResult = service.createQueue(queueInfo);
					result.append("Created queue " + queueResult.getValue().getPath() + CR);

				} catch (ServiceException e)
				{
					LOG.error("Error processing request ", e);
				}
			}
			else
			{
				result.append("Queue " + QUEUE_NAME + " already exists..." + CR);
			}
			
			result.append("Writing message to queue..." + CR);
			BrokeredMessage message = new BrokeredMessage("PCF is a great product!");
			service.sendQueueMessage(QUEUE_NAME, message);
			
		} catch (ServiceException e)
		{
			LOG.error("Error processing request ", e);
		}

		try
		{
			ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
			opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

			while (true)
			{
				result.append("Reading message from queue..." + CR);
				ReceiveQueueMessageResult resultQM = service.receiveQueueMessage(QUEUE_NAME, opts);
				BrokeredMessage message = resultQM.getValue();
				if (message != null && message.getMessageId() != null)
				{
					result.append("Read message from queue, id = " + message.getMessageId() + CR);

					List<String> contents = IOUtils.readLines(message.getBody());
					for (String s:contents)
					{
						result.append("Read message from queue, contents = " + s + CR);
					}
					
					result.append("Deleting message from queue..." + CR);
					service.deleteMessage(message);
				} 
				else
				{
					result.append("Finished reading messages from queue..." + CR);
					break;
				}
			}
		} catch (ServiceException e)
		{
			LOG.error("Error processing request ", e);
		} catch (Exception e)
		{
			LOG.error("Error processing request ", e);
		}

		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("QueueRestController process end");
		return result.toString();
	}

}
