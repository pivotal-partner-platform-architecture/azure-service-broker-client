package io.pivotal.azuresb.storage.table;

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
