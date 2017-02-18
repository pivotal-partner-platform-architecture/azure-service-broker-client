# To create the DocumentDB service instance on PCF
Refer the documentation here: https://docs.pivotal.io/partners/azure-sb/using.html

Once the Service Instance is created....

# Buld your project
edit the application.properties under src/main/resources of your Client project and add the following properties
* azure.documentdb.service.instance=[CF SERVICE INSTANCE NAME]
* azure.documentdb.resource.id=[NAME OF THE DOCUMENTDB COLLECTION]

# To push to PCF
Login to your PCF environment
Go to your azure-sb-service-bus-client folder.
Edit the manifest.yml file appropriately. Make sure your service instance name is correct
Run "cf push"

# Try the DocumentDB Demo
Get the URL from the output of the "cf-push" command, and append
the following endpoints:

* "/read" - this will display all the documents in your Collection
* "/write?name=[NAME]&category=[CATEGORY]" - this will create a document in your Collection with the given name and category
* "update?id=[DOCUMENT ID]&isComplete=[TRUE/FALSE]" - this will update the Category of the Document with given ID
* "/delete?id=[DOCUMENT ID]" - this will delete the document with the given ID
