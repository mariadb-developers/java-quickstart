# Spring Boot with Spring Data JPA quickstart

Spring Boot is a popular Java framework that simplifies the implementation of production-grade applications. Spring Data provides a programming model for data access. Spring Data supports several Java persistence frameworks. This quickstart guide uses Spring Data JPA. If you want to use JPA without Spring, see the [JPA/Hibernate quickstart](https://github.com/mariadb-developers/java-quickstart/tree/main/jpa-hibernate).

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA.

## LT;DR (kinda)

Add the the Spring Data JPA dependency and the [MariaDB JDBC Driver](https://mariadb.com/docs/clients/mariadb-connectors/connector-j/):

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
	<groupId>org.mariadb.jdbc</groupId>
	<artifactId>mariadb-java-client</artifactId>
</dependency>
```

Configure the database connection in the **application.properties** file:

```properties
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MariaDBDialect
spring.datasource.url=jdbc:mariadb://localhost:3306/demo
spring.datasource.username=user
spring.datasource.password=Password123!
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

Create an `Entity`:

```java
@Entity
@Table(name = "programming_language")
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pl_id")
    private Integer id;

    @Column(name = "pl_name")
    private String name;

    @Column(name = "pl_rating")
    private Integer rating;

    ... equals, hashCode, getters and setters ...
}

```

Create a `JpaRepository` interface:

```java
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Integer> {

}
```

Run CRUD operations on the database using the methods of `JpaRepository`. See the [documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories) for more details.

## Requirements
- Java 21 or later. Previous versions should work (update the version in the **pom.xml** file).
Apache Maven.
- MariaDB server.

## Preparing the database

See the instructions [here](../README.md).

## Running the app

Run the following in the command line:

``` Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jpa-hibernate/
mvn package
java -jar target/spring-boot-jpa-0.0.1-SNAPSHOT.jar
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
