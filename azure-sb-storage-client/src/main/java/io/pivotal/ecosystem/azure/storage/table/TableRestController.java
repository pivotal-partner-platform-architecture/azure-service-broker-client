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

package io.pivotal.ecosystem.azure.storage.table;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

@RestController
public class TableRestController
{
	private static final Logger LOG = LoggerFactory.getLogger(TableRestController.class);
	private static final String CR = "</BR>";

	@Autowired
	private CloudStorageAccount account;

	@RequestMapping(value = "/table", method = RequestMethod.GET)
	public String processTable(HttpServletResponse response)
	{
		LOG.info("TableRestController processTable start...");
		StringBuffer result = new StringBuffer();

		try
		{
			result.append("Connecting to storage account..." + CR);

			// Create the table client.
			CloudTableClient tableClient = account.createCloudTableClient();

			// Create the table if it doesn't exist.
			result.append("Creating product table..." + CR);
			String tableName = "product";
			CloudTable cloudTable = tableClient.getTableReference(tableName);
			cloudTable.createIfNotExists();

			ProductEntity product = new ProductEntity(ProductType.SOFTWARE, "pcf", "Pivotal Cloud Foundry");

			result.append("Storing new product in table..." + CR);
			TableOperation insert = TableOperation.insertOrReplace(product);
			cloudTable.execute(insert);

			result.append("Retrieving product from table..." + CR);
			TableOperation retrieve = TableOperation.retrieve(ProductType.SOFTWARE.toString(), "pcf", ProductEntity.class);

			ProductEntity specificEntity = cloudTable.execute(retrieve).getResultAsType();

			result.append("Product = " + specificEntity + CR);

		} catch (Exception e)
		{
			LOG.error("Error processing request ", e);
		}

		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("TableRestController processTable end");
		return result.toString();
	}

}
