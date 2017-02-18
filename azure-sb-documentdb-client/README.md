# To create the DocumentDB service instance on PCF
Refer the documentation here: https://docs.pivotal.io/partners/azure-sb/using.html

Once the Service Instance is created....

# Buld your project
edit the application.properties under src/main/resources of your Client project and add the following properties
* _azure.documentdb.service.instance=[CF SERVICE INSTANCE NAME]_
* _azure.documentdb.resource.id=[NAME OF THE DOCUMENTDB COLLECTION]_

# To push to PCF
Login to your PCF environment
Go to your azure-sb-service-bus-client folder.
Edit the manifest.yml file appropriately. Make sure your service instance name is correct
Run _cf push_

# Try the DocumentDB Demo
Get the URL from the output of the _cf-push_ command, and append
the following endpoints:

* _"/read"_ - this will display all the documents in your Collection
* _"/write?name=[NAME]&category=[CATEGORY]"_ - this will create a document in your Collection with the given name and category
* _"update?id=[DOCUMENT ID]&isComplete=[TRUE/FALSE]"_ - this will update the Category of the Document with given ID
* _"/delete?id=[DOCUMENT ID]"_ - this will delete the document with the given ID
