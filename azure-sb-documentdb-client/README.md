# About azure-sb-documentdb-client
This is a simple Spring Boot application that reads and writes into an Azure DocumentDB database.
The app exposes 4 REST endpoints - _read, write, update and delete_
This app actually is the sample java application described in this link below but refactored to fit into the Spring Boot Starter model.
https://github.com/Azure/azure-documentdb-java

# How this works?
When you create a Service Instance of DocumentDB in Cloud Foundry, the Azure Service Broker creates a DocumentDB database in your Account. 
You bind this service instance with your App. 
Inside the DocumentDB, you can create multiple Collections, and inside each Collection you can create multiple Documents.
The App provides CRUD REST endpoints to create documents into the Collection you specified. If the Collection does not exist in the DocumentDB database, it will create it. 

# To create the DocumentDB service instance on PCF
Refer the documentation here: https://docs.pivotal.io/partners/azure-sb/using.html

Once the Service Instance is created....

# Build your project
edit the _application.properties_ under _src/main/resources_ of your Client project and add the following properties
* _azure.documentdb.service.instance=[CF SERVICE INSTANCE NAME]_
* _azure.documentdb.resource.id=[NAME OF THE DOCUMENTDB COLLECTION]_

# To push to PCF
Login to your PCF environment
Go to your azure-sb-service-bus-client folder.
Edit the _manifest.yml_ file appropriately. Make sure your service instance name is correct
Run _"cf push"_

# Try the DocumentDB Demo
Get the URL from the output of the _"cf push"_ command, and append
the following endpoints:

* _"/read"_ - this will display all the documents in your Collection
* _"/write?name=[NAME]&category=[CATEGORY]"_ - this will create a document in your Collection with the given name and category
* _"update?id=[DOCUMENT ID]&isComplete=[TRUE/FALSE]"_ - this will update the Category of the Document with given ID
* _"/delete?id=[DOCUMENT ID]"_ - this will delete the document with the given ID
