# To create the Redis service instance on PCF
Before you can create the service, you'll need to create a Redis Cache on Azure, with either a new or existing resource group, then
create a JSON file (ex. azure-redis.json) with the configuration details for your service instance.
Specify your resource group name for REDIS_RESOURCE_GROUP_NAME, a new
value for REDIS_CACHE_NAME, and your Azure location for LOCATION

```
{
  "resourceGroup": "REDIS_RESOURCE_GROUP_NAME",
  "cacheName": "REDIS_CACHE_NAME",
  "parameters": {
    "location": "LOCATION",
    "enableNonSslPort": false,
    "sku": {
      "name": "Basic",
      "family": "C",
      "capacity": 0
    }
  }
}

```

Now you can create the new service

```
cf create-service azure-rediscache basic myredis -c ./azure-redis.json
```

# To push to PCF
Login to your PCF environment and run "cf push" from the azure-sb-redis-client folder.

The manifest.yml file specifies meta-data about the application, including the service binding to the "myredis" service instance.


```
---
applications:
- name: azure-redis-client
  memory: 1G
  path: ./target/azure-sb-redis-client-0.0.1-SNAPSHOT.jar
  random-route: true
  services:
    - myredis
```

# Try the Redis Demo
Get the URL from the output of the "cf-push" command, and append
the following endpoints:

* "/redis" - this demo simply puts a key/value pair into redis and retrieves it








