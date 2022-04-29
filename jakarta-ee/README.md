# Jakarta EE / Eclipse GlassFish quickstart

[Jakarta EE](https://jakarta.ee) is a set of vendor-neutral APIs for developing Java cloud-native applications that require enterprise features like distributed computing and web services.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA in a Jakarta EE application deployed to [Eclipse GlassFish](https://glassfish.org).

## LT;DR (kinda)

Add the Jakarta EE API:

```xml
<dependency>
	<groupId>jakarta.platform</groupId>
	<artifactId>jakarta.jakartaee-api</artifactId>
	<version>9.1.0</version>
	<scope>provided</scope>
</dependency>
```

Define a Persistence Unit:

```xml
<persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="2.0">
	<persistence-unit name="jakarta-ee-demo" transaction-type="JTA">
		<jta-data-source>mariadb-database</jta-data-source>
	</persistence-unit>
</persistence>
```

Implement a JPA Entity and define a named query ([JPQL](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#a4665)):

```Java
@Entity
@Table(name = "programming_language")
@NamedQuery(name = "topProgrammingLanguages", query = "from ProgrammingLanguage pl where pl.rating > :rating")
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

Inject an `EntityManager`:

```java
@PersistenceContext(unitName = "jakarta-ee-demo")
private EntityManager entityManager;
```

Execute queries:

```java
TypedQuery<ProgrammingLanguage> query = entityManager.createNamedQuery(
		"topProgrammingLanguages", ProgrammingLanguage.class);
query.setParameter("rating", 5);
List<ProgrammingLanguage> programmingLanguages = query.getResultList();
```

## Requirements
- Java 17 or later. Previous versions should work (update the version in the pom.xml file).
Apache Maven.
- Eclipse GlassFish 6.2.5 or later.
- MariaDB server. If you don't want to install anything extra, try creating a free [SkySQL account](https://cloud.mariadb.com).
- MariaDB Connector/J 3.0.4 or later.
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Running the app

Prepare the database:

```sql
CREATE DATABASE jakartaee_demo;
CREATE USER 'user'@'%';
GRANT ALL ON jakartaee_demo.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

USE jakartaee_demo;
CREATE TABLE programming_language(
	pl_id INT PRIMARY KEY AUTO_INCREMENT,
	pl_name VARCHAR(50) NOT NULL UNIQUE,
	pl_rating INT
);
```

[Download MariaDB Connector/J](https://mariadb.com/downloads/connectors/connectors-data-access/java8-connector) (select Java8+ connector) and place the JAR in the **lib** directory of the fault GlassFish domain:

```
cp ~/Downloads/mariadb-java-client-3.0.4.jar [GLASSFISH_HOME]/glassfish/domains/domain1/lib
```

Start the GlassFish application server:

```
[GLASSFISH_HOME]/bin/asadmin start-domain
```

Configure the database connection pool using the [Administration Console](https://glassfish.org/docs/latest/administration-guide/overview.html#GSADG00698) by going to **Resources > JDBC > JDBC Connection Pools**. Click **New** and fill in the following details:

 * **Pool Name**: `MariaDB`
 * **Resource Type**: `java.sql.Driver`

Click **Next** and fill in the following details:

 * **Driver Classname**: `org.mariadb.jdbc.Driver`

Click **Finish**.

In the JDBC Connection Pool list, click the newly created pool, select the **Additional Properties** tab, and add the following properties using the **Add Property** button:

 * **url**: `jdbc:mariadb://localhost:3306/jakartaee_demo`
 * **user**: `user`
 * **password**: `password`

> If you are using [MariaDB SkySQL](https://mariadb.com/products/skysql/), enable SSL and specify the path to the CA chain file that you can download from the [SkySQL Portal](https://cloud.mariadb.com):
> 
> `jdbc:mariadb://demo-db0000xxxx.mdb000xxxx.db.skysql.net:5047/jakartaee_demo?useSsl=true&serverSslCert=/path/to/your/skysql_chain.pem`

Go to **Resources > JDBC > JDBC Resources**. Click **New** and fill in the following details:

* **JNDI Name**: `mariadb-database`
* **Pool Name**: `MariaDB`

Build the WAR file:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jakarta-ee/
mvn package
```

Deploy the WAR file to GlassFish using the Administration Console. Go to **Applications** and click te button next to the **Location** field, select the WAR file (**java-quickstart/jakarta-ee/target/jakarta-ee-1.0-SNAPSHOT.war**), and click **Ok**.

You should be able to see new rows in the `programming_language` table in the database as well as log messages confirming that data was deleted, created, and read. Go to **Monitoring Data > server** and click the **View Log Files** button.