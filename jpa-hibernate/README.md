# JPA/Hibernate quickstart

Jakarta Persistence API (JPA, formerly Java Persistence API) is a specification that allows Java applications to connect
and consume databases and map Java objects to database tables. JPA is built on top of
[JDBC](https://github.com/mariadb-developers/java-quickstart/tree/main/jdbc).

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA.

## LT;DR (kinda)

Add the JDBC Driver ([check](https://mariadb.com/docs/clients/mariadb-connectors/connector-j/#latest-software-releases)
the latest version), JPA, and Hibernate ([check](https://hibernate.org/orm/releases) the latest version):

```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core-jakarta</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>LATEST</version>
</dependency>
```

Configure a *Persistence Unit* with the database connection details:

```xml
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
             version="2.0">
    <persistence-unit name="persistence-unit-name" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mariadb://localhost:3306/database_name"/>
            <property name="jakarta.persistence.jdbc.user" value="user"/>
            <property name="jakarta.persistence.jdbc.password" value="Password123!"/>
        </properties>
    </persistence-unit>
</persistence>
```

Create an `EntityManagerFactory` using the configured Persistence Unit name:

```java
public class Example {
    private static EntityManagerFactory emf;
    
    public Example() {
        emf = Persistence.createEntityManagerFactory("persistence-unit-name");
    }
}
```

Create an `EntityManager` and start a transaction to query the database:

```java
EntityManager entityManager = emf.createEntityManager();
EntityTransaction transaction = entityManager.getTransaction();
transaction.begin();
boolean success = false;
try {
    ... use the entityManager here ...
        
    success = true;
    return returnValue;

} finally {
    if (success) {
        transaction.commit();
    } else {
        transaction.rollback();
    }
}
```

## Requirements
- Java 21 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven.
- MariaDB server.
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Preparing the database

See the instructions [here](../README.md).

## Running the app

Prepare the database:

```sql
CREATE DATABASE demo;
CREATE USER 'user'@'%' IDENTIFIED BY 'Password123!';
GRANT SELECT, INSERT, UPDATE, DELETE ON demo.* TO 'user'@'%';
GRANT DROP, CREATE on demo.* TO 'user'@'%';

USE demo;
CREATE TABLE programming_language(
	pl_id INT PRIMARY KEY AUTO_INCREMENT,
	pl_name VARCHAR(50) NOT NULL UNIQUE,
	pl_rating INT
);
```

Run the following in the command line:

```Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jpa-hibernate/
mvn package
java -jar target/jpa-hibernate-1.0-SNAPSHOT.jar
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

Read the [tutorial](https://dzone.com/articles/getting-started-with-jpahibernate) or watch the video for detailed steps on how to implement this example from scratch:

[![JPA/Hibernate tutorial for beginners](https://img.youtube.com/vi/UVo2SRR-ZRM/hqdefault.jpg)](https://www.youtube.com/watch?v=UVo2SRR-ZRM)
