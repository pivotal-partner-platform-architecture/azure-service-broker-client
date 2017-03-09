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

package io.pivotal.ecosystem.azure.storage.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;

@RestController
public class FileRestController 
{
	private static final Logger LOG = LoggerFactory
			.getLogger(FileRestController.class);
	private static final String CR = "</BR>";

	@Autowired
	private CloudStorageAccount account;

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String process(HttpServletResponse response) {
		LOG.info("FileRestController process start...");
		StringBuffer result = new StringBuffer();

		try {
			result.append("Connecting to storage account..." + CR);
			result.append("Creating file client..." + CR);
			CloudFileClient fileClient = account.createCloudFileClient();
			
			result.append("Creating file share..." + CR);
			CloudFileShare share = fileClient.getShareReference("productshare");
			if (share.createIfNotExists()) {
				result.append("Created file share..." + CR);
			}
			
			CloudFileDirectory rootDir = share.getRootDirectoryReference();

			result.append("Creating products directory..." + CR);
			CloudFileDirectory dir = rootDir.getDirectoryReference("products");

			if (dir.createIfNotExists()) {
				result.append("Created products directory..." + CR);
			} else {
				result.append("Products directory already exists..." + CR);
			    System.out.println("sampledir already exists");
			}
			
			result.append("Creating Product.txt file..." + CR);
			CloudFile cloudFile = dir.getFileReference("Product.txt");
			cloudFile.create(1024L);
			cloudFile.openWriteExisting();
			String contents = "PCF is a great product!";

			result.append("Writing to file..." + CR);
			InputStream in = new ByteArrayInputStream(contents.getBytes());
			cloudFile.upload(in, contents.length());
			
			result.append("Reading from file..." + CR);
			String output = cloudFile.downloadText();
			
			result.append("Contents = " + output + CR);

		} catch (Exception e) {
			LOG.error("Error processing request ", e);
		}

		result.append("Processed Date = " + new Date(System.currentTimeMillis()) + CR);

		LOG.info("FileRestController process end");
		return result.toString();
	}

}
