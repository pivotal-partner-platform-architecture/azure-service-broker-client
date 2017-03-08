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

package io.pivotal.azuresb.storage.blob;

import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

@RestController
public class StorageRestController {

	private static final Logger LOG = LoggerFactory.getLogger(StorageRestController.class);

	public static final String IMAGE_PATH = "https://raw.githubusercontent.com/mjeffries-pivotal/pcf-samples/master/images/azure-pcf.jpg";
	
	@Autowired
	private CloudStorageAccount account;

	@RequestMapping(value = "/blob", method = RequestMethod.GET)
	public @ResponseBody void showBlob(HttpServletResponse response) {
		try {
			LOG.info("showBlob start");
			URL u = new URL(IMAGE_PATH);
			InputStream is = u.openStream();
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    int imageSize = IOUtils.copy(is, response.getOutputStream());
		    
		    LOG.debug("Connecting to storage account...");
			CloudBlobClient serviceClient = account.createCloudBlobClient();

			// Container name must be lower case.
			CloudBlobContainer container = serviceClient.getContainerReference("myimages");
			container.createIfNotExists();

			// Upload an image file.
			LOG.debug("Uploading image...");
			CloudBlockBlob blob = container.getBlockBlobReference("image1.jpg");
			blob.upload(new URL(IMAGE_PATH).openStream(), imageSize);
			LOG.debug("Uploading image complete");
			
		} catch (Exception e) {
			LOG.error("Error processing request ", e);
		}
	}
	
}
