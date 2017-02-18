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


# Try the DocumentDB Demo

1. Checkout the repository from GIT
git clone https://github.com/pivotal-alliances-immersion/azure-service-broker-client.git

2.  Edit the appropriate config files
edit the under _azure-service-broker-client/azure-sb-documentdb-client/src/main/resources/application.properties_ and edit the following properties
* _azure.documentdb.service.instance=[CF SERVICE INSTANCE NAME]_  (this will be the DocumentDB service instance name from the step above)
* _azure.documentdb.resource.id=[NAME OF THE DOCUMENTDB COLLECTION]_  (give some name to your Document Collection. The _write_ REST call will create this Collection if it does not exist)

3. Edit the CF Manifest
edit the under _azure-service-broker-client/azure-sb-documentdb-client/manifest.yml_ (make sure the service instance name is correct)

4. Build your project
Run _mvn clean package_ from _azure-service-broker-client/_ folder

5. Push the App to PCF
run _cf push_ from _azure-service-broker-client/azure-sb-documentdb-client_ folder

6. Run the App
Get the URL from the output of the _"cf push"_ command, and append
the following endpoints:

* _"/read"_ - this will display all the documents in your Collection
* _"/write?name=[NAME]&category=[CATEGORY]"_ - this will create a document in your Collection with the given name and category
* _"update?id=[DOCUMENT ID]&isComplete=[TRUE/FALSE]"_ - this will update the Category of the Document with given ID
* _"/delete?id=[DOCUMENT ID]"_ - this will delete the document with the given ID
