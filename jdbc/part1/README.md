# JDBC Quickstart - Part 1 (database connections)

This Maven project shows the minimal configuration needed to connect to
MariaDB databases using Java Database Connectivity (JDBC) without any
other persistence frameworks.

## TL;DR

Add the JDBC Driver ([check](https://mariadb.com/docs/clients/mariadb-connectors/connector-j/#latest-software-releases)
the latest version):

```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>
```

Open the connection (alternatively use a try-catch block to close the connection automatically):

```java
Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/database_name",
        "user", "password"
);
```

Close the connection (if not using a try-catch block):

```java
connection.close();
```

## Requirements

- Java 17 or later. Previous versions should work (update the version
  in the **pom.xml** file).
- [Apache Maven](https://maven.apache.org).
- MariaDB server. If you don't want to install
  anything extra, try creating a free [SkySQL account](https://cloud.mariadb.com).
- An SQL client tool like `mariadb`, DBeaver, or an SQL integration for
  your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE jdbc_demo;
CREATE USER 'user'@'%';
GRANT ALL ON jdbc_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
```

Run the following from the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/part1/
mvn package
java -jar  java -jar target/jdbc-demo-1.0-SNAPSHOT.jar
```

Screenshot of the output:

![Output](https://dz2cdn1.dzone.com/storage/temp/15510536-3-jdbc-output.png)

## Tutorial

Read the [tutorial](https://dzone.com/articles/jdbc-tutorial-part-1-connecting-to-a-database)
or watch the video for detailed steps on how to implement this example from scratch:

[![JDBC Tutorial part 1/3 – Database connections](https://img.youtube.com/vi/ceAev_93p3s/hqdefault.jpg)](https://www.youtube.com/watch?v=ceAev_93p3s)
