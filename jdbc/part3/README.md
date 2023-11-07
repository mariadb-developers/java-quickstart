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
jdbcUrl=jdbc:mariadb://127.0.0.1:3306/database_name
dataSource.username=user
dataSource.password=Password123!
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

- Java 21 or later. Previous versions should work (update the version
  in the **pom.xml** file).
- [Apache Maven](https://maven.apache.org).
- MariaDB server.
- An SQL client tool like `mariadb`, DBeaver, or an SQL integration for
  your IDE.

## Preparing the database

See the instructions [here](../../README.md).

## Running the app

Run the following from the command line:

```Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/part3/
mvn package
java -jar  java -jar target/jdbc-demo-1.0-SNAPSHOT.jar
```

## Check the output

You should see the output in the terminal.

You can also connect to the database and see the data in the `programming_language` table:

```shell
mariadb-shell --dsn mariadb://user:'Password123!'@127.0.0.1
```

Run the following query:

```SQL
SELECT * FROM demo.programming_languages;
```
