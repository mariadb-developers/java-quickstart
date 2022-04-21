# MyBatis quickstart

[MyBatis](https://mybatis.org/mybatis-3) is a persistence framework that maps SQL commands results to Java methods.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using MyBatis in a Spring Boot application.

## LT;DR (kinda)

Add the MariaDB JDBC Driver and MyBatis dependencies:

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>

<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>
```

Configure the database connection in the **application.properties** file:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/mybatis_demo
spring.datasource.username=user
spring.datasource.password=password
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
- Java 17 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven.
- MariaDB server. If you don't want to install anything extra, try creating a free [SkySQL account](https://cloud.mariadb.com).
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE mybatis_demo;
CREATE USER 'user'@'%';
GRANT ALL ON mybatis_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

USE mybatis_demo;
CREATE TABLE programming_language(
    name VARCHAR(50) NOT NULL UNIQUE,
    rating INT
);
```

Run the following in the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/spring-boot-mybatis/
mvn package
java -jar target/spring-boot-mybatis-0.0.1-SNAPSHOT.jar
```
