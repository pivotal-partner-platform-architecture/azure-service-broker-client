# azure-service-broker-client
Demo application for client applications consuming azure services exposed through the PCF Azure Service Broker

# Description
This demo is composed of the following applications:

# azure-sb-sql-server-client
This application is a simple SQL Server database client

# azure-sb-storage-client
This application is a storage client using the Blob, Table, Queue, Disk, and File storage capabilities

# azure-sb-documentdb-client
This application is a simple DocumentDB client

# azure-sb-spring-boot-autoconfigure
Spring Boot starter project includes libraries and components

# azure-sb-spring-boot-starter
Spring Boot starter project, referenced from all the sample client projects

# Build the projects
To build all the projects, just open a shell in the root of the project, and run
```
mvn clean package
```

# Run the projects
[SQL Server client] (azure-sb-sql-server-client/README.md)

[Storage client] (azure-sb-storage-client/README.md)

[Service Bus client] (azure-sb-service-bus-client/README.md)

[Redis client] (azure-sb-redis-client/README.md)

[DocumentDB client] (azure-sb-documentdb-client/README.md)
