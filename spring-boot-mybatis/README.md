# MyBatis quickstart

[MyBatis](https://mybatis.org/mybatis-3) is a persistence framework that maps SQL commands results to Java methods.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using MyBatis in a Spring Boot application.

## LT;DR (kinda)

Add the MariaDB JDBC Driver and MyBatis dependencies:

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>LATEST</version>
</dependency>

<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>
```

Configure the database connection in the **application.properties** file:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/demo
spring.datasource.username=user
spring.datasource.password=Password123!
```

Implement a mapper that you can inject in any Spring bean:

```java
@Mapper
public interface ProgrammingLanguageMapper {

    @Select("""
            SELECT name, rating
            FROM programming_language
            WHERE rating > #{rating}
            """)
    List<ProgrammingLanguage> findTopProgrammingLanguages(@Param("rating") int rating);

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
Run the following in the command line:

```Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/spring-boot-mybatis/
mvn package
java -jar target/spring-boot-mybatis-0.0.1-SNAPSHOT.jar
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
