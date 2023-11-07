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
- Java 21 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven
- MariaDB server
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE

## Preparing the database

See the instructions [here](../README.md).

## Running the app

Run the following in the command line:

```Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/spring-boot-jooq/
mvn package
java -jar target/spring-boot-jooq-0.0.1-SNAPSHOT.jar
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

## Webinar

Watch the webinar [Type-Safe SQL with jOOQ](https://go.mariadb.com/22Q3-WBN-GLBL-DBaaS-Type-safe-SQL-jOOQ-on-SkySQL-2022-04-28_Registration-LP.html).

Also check out [this repository](https://github.com/simasch/jooq-mariadb) which uses the [Sakila database](https://www.jooq.org/sakila) and adds [FlyWay](https://flywaydb.org/).
