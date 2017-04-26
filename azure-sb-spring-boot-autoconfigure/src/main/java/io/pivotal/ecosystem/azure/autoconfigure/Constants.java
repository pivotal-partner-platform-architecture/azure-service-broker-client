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

package io.pivotal.ecosystem.azure.autoconfigure;

public class Constants
{
	// Property namespaces
	public static final String NAMESPACE_STORAGE = "azure.storage.account";
	public static final String NAMESPACE_SERVICE_BUS = "azure.servicebus";
	public static final String NAMESPACE_REDIS = "azure.redis";
	public static final String NAMESPACE_DOCUMENTDB = "azure.documentdb";

	// VCAP credential key names
	public static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
	public static final String PRIMARY_ACCESS_KEY = "primary_access_key";
	
	public static final String SHARED_ACCESS_NAME = "shared_access_key_name";
	public static final String SHARED_ACCESS_KEY_VALUE = "shared_access_key_value";
	public static final String NAMESPACE_NAME = "namespace_name";

	public static final String HOST_NAME = "hostname";
	public static final String SSL_PORT = "sslPort";
	public static final String PRIMARY_KEY = "primaryKey";

	public static final String HOST_ENDPOINT = "documentdb_host_endpoint";
	public static final String MASTER_KEY = "documentdb_master_key";

}
