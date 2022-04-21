# jOOQ quickstart

[jOOQ](https://www.jooq.org) is a Java database-mapping library that generates Java classes from SQL databases to allow running queries in a type-safe fashion using a fluent API.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using jOOQ in a Spring Boot application.

## LT;DR (kinda)

Add the MariaDB JDBC Driver and jOOQ dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jooq</artifactId>
</dependency>

<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>
```

Configure the database connection in the **application.properties** file:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/jooq_demo
spring.datasource.username=user
spring.datasource.password=password
```

Autowire a `DSLContext` and use it to run SQL statements:

```java
List<ProgrammingLanguageRecord> programmingLanguages = dslContext
        .select(PROGRAMMING_LANGUAGE.NAME, PROGRAMMING_LANGUAGE.RATING)
        .from(PROGRAMMING_LANGUAGE)
        .where(PROGRAMMING_LANGUAGE.RATING.greaterThan(3))
        .orderBy(PROGRAMMING_LANGUAGE.RATING.desc())
        .fetchInto(ProgrammingLanguageRecord.class);
```

## Requirements
- Java 17 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven.
- MariaDB server. If you don't want to install anything extra, try creating a free [SkySQL account](https://cloud.mariadb.com).
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE jooq_demo;
CREATE USER 'user'@'%';
GRANT ALL ON jooq_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

USE jooq_demo;
CREATE TABLE programming_language(
    name VARCHAR(50) NOT NULL UNIQUE,
    rating INT
);
```

Run the following in the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/spring-boot-jooq/
mvn package
java -jar target/spring-boot-jooq-0.0.1-SNAPSHOT.jar
```
[//]: # (insert screenshot)

## Webinar

Watch the webinar [Type-Safe SQL with jOOQ on SkySQL](https://go.mariadb.com/22Q3-WBN-GLBL-DBaaS-Type-safe-SQL-jOOQ-on-SkySQL-2022-04-28_Registration-LP.html).