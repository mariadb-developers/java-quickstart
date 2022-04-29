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
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core-jakarta</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>3.0.0</version>
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
            <property name="jakarta.persistence.jdbc.password" value="password"/>
        </properties>
    </persistence-unit>
</persistence>
```

> If you are using [MariaDB SkySQL](https://mariadb.com/products/skysql/), enable SSL and specify the path to the CA chain file that you can download from the [SkySQL Portal](https://cloud.mariadb.com):
> 
> `jdbc:mariadb://demo-db0000xxxx.mdb000xxxx.db.skysql.net:5047?useSsl=true&serverSslCert=/path/to/your/skysql_chain.pem`

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
- Java 17 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven.
- MariaDB server. If you don't want to install anything extra, try creating a free [SkySQL account](https://cloud.mariadb.com).
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE jpa_demo;
CREATE USER 'user'@'%';
GRANT ALL ON jpa_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
```

Run the following in the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jpa-hibernate/
mvn package
java -jar target/jpa-hibernate-1.0-SNAPSHOT.jar
```
[//]: # (insert screenshot)
## Tutorial

Read the [tutorial](https://dzone.com/articles/getting-started-with-jpahibernate) or watch the video for detailed steps on how to implement this example from scratch:

[![JPA/Hibernate tutorial for beginners](https://img.youtube.com/vi/UVo2SRR-ZRM/hqdefault.jpg)](https://www.youtube.com/watch?v=UVo2SRR-ZRM)
