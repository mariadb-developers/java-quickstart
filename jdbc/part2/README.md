# JDBC Quickstart - Part 2 (executing SQL statements)

This Maven project shows how to run SQL statements to perform
CRUD (Create, Read, Update, Delete) operations.

## TL;DR

Create (`INSERT`), update (`UPDATE`), and delete (`DELETE`):

```java
try (PreparedStatement statement = connection.prepareStatement("""
            INSERT INTO table_name(column1, column2)
            VALUES (?, ?)
        """)) {
    statement.setString(1, someString);
    statement.setInt(2, someInteger);
    int rowsInserted = statement.executeUpdate();
}
```

Read (`SELECT`):

```java
try (PreparedStatement statement = connection.prepareStatement("""
            SELECT column1, column2
            FROM table_name
        """)) {
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
        String val1 = resultSet.getString(1); // by column index
        int val2 = resultSet.getInt("column2"); // by column name
        // ... use val1 and val2 ...
    }
}
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

USE jdbc_demo;
CREATE TABLE programming_language(
    name VARCHAR(50) NOT NULL UNIQUE,
    rating INT
);
```

Run the following from the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/part2/
mvn package
java -jar  java -jar target/jdbc-demo-1.0-SNAPSHOT.jar
```

## Tutorial

Read the [tutorial](https://dzone.com/articles/jdbc-tutorial-part-2-running-sql-queries) or watch the video for detailed steps on how to implement this example from scratch:

[![JDBC Tutorial part 1/3 – Database connections](https://img.youtube.com/vi/nWhZl4cMjRw/hqdefault.jpg)](https://www.youtube.com/watch?v=nWhZl4cMjRw)
