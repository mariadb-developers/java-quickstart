# JDBC Quickstart - Part 3 (connection pooling)

This Maven project shows how to use the HikariCP connection pool
with MariaDB.

## TL;DR

Add HikariCP in the **pom.xml** file ([check](https://search.maven.org/artifact/com.zaxxer/HikariCP) the latest version):

```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>LATEST</version>
</dependency>
```

Configuration file (**src/main/resources/database.properties**):

```properties
jdbcUrl=jdbc:mariadb://localhost:3306/database_name
dataSource.username=user
dataSource.password=password
```

Create a `DataSource` at application start:

```java
HikariConfig hikariConfig = new HikariConfig("/database.properties");
HikariDataSource dataSource = new HikariDataSource(hikariConfig); 
```

Get a `Connection` object:

```java
try (Connection connection = dataSource.getConnection()) {
    // ... use the connection object to run SQL statements ...
}
```

Close the `DataSource` at application shutdown:

```java
dataSource.close();
```

## Requirements

- Java 17 or later. Previous versions should work (update the version
  in the **pom.xml** file).
- [Apache Maven](https://maven.apache.org).
- MariaDB server. If you don't want to install
  anything extra, try creating a
  [free SkySQL account](https://mariadb.com/products/skysql)).
- An SQL client tool like `mariadb`, DBeaver, or an SQL integration for
  your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE jdbc_demo;
CREATE USER 'user'@'%';
GRANT ALL ON jdbc_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

USE jdbc_demo;
CREATE TABLE programming_language(
    name VARCHAR(50) NOT NULL UNIQUE,
    rating INT
);
```

Run the following from the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/part3/
mvn package
java -jar  java -jar target/jdbc-demo-1.0-SNAPSHOT.jar
```

## Tutorial

*(Work in progress. Stay tuned!)*
