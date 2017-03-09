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

import com.microsoft.azure.storage.table.TableServiceEntity;

public class ProductEntity extends TableServiceEntity
{
	public ProductEntity(ProductType productType, String name, String description) 
	{
        this.partitionKey = productType.toString();
        this.rowKey = name;
        this.description = description;
    }

    public ProductEntity() { }

    String description;

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProductEntity [partitionKey="+ partitionKey + 
				", rowKey=" + rowKey + 
				", description=" + description + 
				", etag=" + etag + 
				", timeStamp=" + timeStamp + "]";
	}

}
