![Build Status](https://build.spring.io/plugins/servlet/wittified/build-status/ASBS-BLD)
# azure-service-broker-client
Demo application for client applications consuming azure services exposed through the PCF Azure Service Broker.  These examples leverage a common Spring Boot Starter project for Azure services, which make it easier to isolate Azure dependencies, and inject commonly used Azure objects into your client applications.

Most of the applications are written to support a single service instance, which can be automatically
injected into your code.  Occasionally you need to support two instances of the same service type, which
requires some additional coding.  For this case, see the azure-sb-storage-client example.

# Description
This demo is composed of the following applications:

## azure-sb-sql-server-client
This application is a simple SQL Server database client

## azure-sb-storage-client
This application is a storage client using the Blob, Table, Queue, Disk, and File storage capabilities.  This
application also demonstrates how to support two different storage service instances bound to the same
application.  In this case, some code is required to connect the right client object to the right
service instance.

## azure-sb-documentdb-client
This application is a simple DocumentDB client

## azure-sb-spring-boot-autoconfigure
Spring Boot starter project includes libraries and components

## azure-sb-spring-boot-starter
Spring Boot starter project, referenced from all the sample client projects

# Build the projects
To build all the projects, just open a shell in the root of the project, and run
```
mvn clean package
```

# Run the projects
[SQL Server client](azure-sb-sql-server-client/README.md)

[Storage client](azure-sb-storage-client/README.md)

[Service Bus client](azure-sb-service-bus-client/README.md)

[Redis client](azure-sb-redis-client/README.md)

[DocumentDB client](azure-sb-documentdb-client/README.md)
