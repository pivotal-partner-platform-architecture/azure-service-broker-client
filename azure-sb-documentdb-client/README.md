# To create the Service Bus service instance on PCF
Before you can create the service, you'll need to create a resource group on Azure, then
create a JSON file (ex. azure-servicebus.json) with the configuration details for your service instance.
Specify the resource group name for SERVICE_BUS_RESOURCE_GROUP_NAME, a new
value for NAMESPACE_NAME, and your Azure location for LOCATION

```
{
  "resource_group_name": "SERVICE_BUS_RESOURCE_GROUP_NAME",
  "namespace_name": "NAMESPACE_NAME",
  "location": "LOCATION",
  "type": "Messaging",
  "messaging_tier": "Standard"
}

```

Now you can create the new service

```
cf create-service azure-servicebus standard myservicebus -c ./azure-servicebus.json
```

# To push to PCF
Login to your PCF environment and run "cf push" from the azure-sb-service-bus-client folder.

The manifest.yml file specifies meta-data about the application, including the service binding to the "myservicebus" service instance.


```
---
applications:
- name: azure-service-bus-client
  memory: 1G
  path: ./target/azure-sb-service-bus-client-0.0.1-SNAPSHOT.jar
  random-route: true
  services:
    - myservicebus

```

# Try the Service Bus Demo
Get the URL from the output of the "cf-push" command, and append
the following endpoints:

* "/queue" - this demo uses the Queue service to create a queue, put a message on the queue, then retrieve and delete it.








