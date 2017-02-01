# To create the Storage service instance on PCF
Before you can create the service, you'll need to create a storage resource group on Azure, then
create a JSON file (ex. azure-storage.json) with the configuration details for your service instance.
Specify your newly created resource group name for STORAGE_RESOURCE_GROUP_NAME, a new
value for STORAGE_ACCOUNT_NAME, and your location for LOCATION

```
{
  "resource_group_name": "STORAGE_RESOURCE_GROUP_NAME",
  "storage_account_name": "STORAGE_ACCOUNT_NAME",
  "location": "LOCATION",
  "account_type": "Standard_LRS"
}

```

Now you can create the new service

```
cf cs azure-storage standard mystorage -c ./azure-storage.json
```

# To push to PCF
Login to your PCF environment and run "cf push" from the azure-sb-storage-client folder.

The manifest.yml file specifies meta-data about the application, including the service binding to the "mystorage" service instance.


```
---
applications:
- name: azure-storage-client
  memory: 1G
  path: ./target/azure-sb-storage-client-0.0.1-SNAPSHOT.jar
  random-route: true
  services:
    - mystorage
```

# Try the Blob Demo
Get the URL from the output of the "cf-push" command, and append "/blob" to run the blob demo, which retrieves an image from a website
and stores it in a new storage container on Azure (under the storage account name you specified above), and displays it.



