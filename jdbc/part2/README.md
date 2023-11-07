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
cd java-quickstart/jdbc/part2/
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

## Tutorial

Read the [tutorial](https://dzone.com/articles/jdbc-tutorial-part-2-running-sql-queries) or watch the video for detailed steps on how to implement this example from scratch:

[![JDBC Tutorial part 1/3 – Database connections](https://img.youtube.com/vi/nWhZl4cMjRw/hqdefault.jpg)](https://www.youtube.com/watch?v=nWhZl4cMjRw)
