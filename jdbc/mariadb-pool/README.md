# MariaDB Connection Pool Example

This Maven project shows how to use the connection pool included with the MariaDB JDBC driver and
showcases the importance of using pooling when interacting with databases.

## TL;DR

Run the app as is. See the error in the output (`SQLException: too many connections`).
Change `Service` constructor to use a `MariaDbPoolDataSource` and run the app again. Now it works.

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

INSERT INTO
    programming_language(name, rating)
VALUES
    ('Java', 10),
    ('C++', 9);
```

Run the following from the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/mariadb-pool/
mvn package
java -jar target/mariadb-pool-1.0-SNAPSHOT.jar
```

The application simulates concurrent requests to the `Service` class, which reads data from
the MariaDB database (you can configure the connection details in the constructor of this
class). Currently, the application **DOES NOT** use a connection pool, but creates a new
connection every time the service method is requested. 

You'll see that some of the requests to the service work (printint data from the database),
but eventually, you'll find some of them fail. For example, you probably see exceptions like:

```
java.sql.SQLException: Too many connections
```

This happens because the datbase can only have a limited number of opened connections.
A database connection pool maintains a set of connections that threads can "borrow". The
MariaDB JDBC driver (Connector/J) includes a connection pool ready to use.

To use a connection pool, just change the type of `DataSource` instanciated in the `Service`
constructor from `MariaDbDataSource` to `MariaDbPoolDataSource`. Compila and run the app again
and see how it can now handle all the requests without errors.
