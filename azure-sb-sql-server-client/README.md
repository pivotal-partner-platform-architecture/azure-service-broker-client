# To run locally using H2 in memory database
```
java -jar azure-sb-sql-server-client/target/azure-sb-sql-server-client-0.0.1-SNAPSHOT.jar
```

# To create the SQL Server service instance on PCF
Before you can create the service, you'll need to [create a SQL Server instance on Azure](https://docs.microsoft.com/en-us/azure/sql-database/sql-database-get-started), then
create a JSON file with the configuration details for your service instance.
For this example, I used "app-db" as the APP-DATABASE-NAME.  All other values are for the resources
used when creating the SQL Server database in Azure.

```
{
  "resourceGroup": "SQL-SERVER-AZURE-RESOURCE-GROUP",
  "location": "LOCATION",
  "sqlServerName": "SQL-SERVER-NAME",
  "sqlServerParameters": {
      "allowSqlServerFirewallRules": [
          {
              "ruleName": "rule0",
              "startIpAddress": "10.0.0.1",
              "endIpAddress": "10.0.0.254"
          },
          {
              "ruleName": "rule1",
              "startIpAddress": "10.0.1.1",
              "endIpAddress": "10.0.1.254"
          }
      ],
      "properties": {
          "administratorLogin": "YOUR-USERID",
          "administratorLoginPassword": "YOUR-PASSWORD"
      }
  },
  "sqldbName": "APP-DATABASE-NAME",
  "sqldbParameters": {
      "properties": {
          "collation": "SQL_Latin1_General_CP1_CI_AS"
      }
  }
}
```

Now you can create the new service

```
cf cs azure-sqldb basic mydb -c ./azure-sqldb.json
```

# Create the SQL Server table to use
```
USE [app-db]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[description] [nvarchar](50) NULL
)

GO

USE [app-db]
GO

CREATE UNIQUE NONCLUSTERED INDEX [IX_Product_Name] ON [dbo].[Product]
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO


```

# To push to PCF
Login to your PCF environment and run "cf push" from the azure-sb-sql-server-client folder.  When running on PCF, Spring Boot will automatically create the
SQL Server datasource for you based on the "mydb" service bound to the application (only Spring Boot 1.5 or greater).

```
---
applications:
- name: azure-sb-sql-server-client
  memory: 1G
  path: ./target/azure-sb-sql-server-client-0.0.1-SNAPSHOT.jar
  random-route: true
  services:
    - mydb
```
