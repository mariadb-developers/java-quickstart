# Jakarta EE / Eclipse GlassFish quickstart

[Jakarta EE](https://jakarta.ee) is a set of vendor-neutral APIs for developing Java cloud-native applications that require enterprise features like distributed computing and web services.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA in a Jakarta EE application deployed to [Eclipse GlassFish](https://glassfish.org).

## TL;DR (kinda)

Add the Jakarta EE API:

```xml
<dependency>
	<groupId>jakarta.platform</groupId>
	<artifactId>jakarta.jakartaee-api</artifactId>
	<version>10.0.0</version>
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
@NamedQuery(name = "popularProgrammingLanguages", query = "from ProgrammingLanguage pl where pl.rating > :rating order by pl.rating desc")
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
		"popularProgrammingLanguages", ProgrammingLanguage.class);
query.setParameter("rating", 5);
List<ProgrammingLanguage> programmingLanguages = query.getResultList();
```

## Requirements
- [Java 21](https://whichjdk.com/) or later. Previous versions should work (update the version in the **pom.xml** file)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Eclipse GlassFish](https://glassfish.org/download.html) 7.0.9 or later
- [MariaDB Connector/J](https://mariadb.com/downloads/connectors/connectors-data-access/java8-connector) 3.2.0 or later
- An SQL client tool like [MariaDB Shell](https://mariadb.com/downloads/tools/), DBeaver, or an [SQL integration](https://www.youtube.com/watch?v=rJYUTxD-2-M) for your IDE
- MariaDB server ([Enterprise](https://mariadb.com/products/enterprise/) or [Community](https://mariadb.com/products/community-server/))

## Preparing the database

See the instructions [here](../README.md).

## Configuring Glassfish

[Download MariaDB Connector/J](https://mariadb.com/downloads/connectors/connectors-data-access/java8-connector) (select Java8+ connector) and place the JAR in the **lib** directory of the fault GlassFish domain (replace `[GLASSFISH_HOME]` with the directory in which your GlassFish installation resides):

```
cp ~/Downloads/mariadb-java-client-3.2.0.jar [GLASSFISH_HOME]/glassfish/domains/domain1/lib/
```

Start the GlassFish application server:

```
[GLASSFISH_HOME]/bin/asadmin start-domain
```

Configure the database connection pool using the [Administration Console](https://glassfish.org/docs/latest/administration-guide.html#administration-console) (http://localhost:4848) by going to **Resources > JDBC > JDBC Connection Pools**. Click **New** and fill in the following details:

 * **Pool Name**: `MariaDB`
 * **Resource Type**: `java.sql.Driver`

Click **Next** and fill in the following details:

 * **Driver Classname**: `org.mariadb.jdbc.Driver`

Click **Finish**.

In the JDBC Connection Pool list, click on **MariaDB**, select the **Additional Properties** tab, and add the following properties using the **Add Property** button:

 * **url**: `jdbc:mariadb://127.0.0.1:3306/demo`
 * **user**: `user`
 * **password**: `Password123!`

Go to **Resources > JDBC > JDBC Resources**. Click **New** and fill in the following details:

* **JNDI Name**: `mariadb-database`
* **Pool Name**: `MariaDB`

Click **OK**.

## Building and deploying the application

Build the WAR file:

```Bash
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jakarta-ee/
mvn package
```

To deploy the WAR file to GlassFish using the Administration Console, go to **Applications** and click the **Deploy** button. Click on the button next to **Location** and select the WAR file (**java-quickstart/jakarta-ee/target/jakarta-ee-1.0-SNAPSHOT.war**). Click **Ok**.

## Check the output

Go to **Monitoring Data > server** and click either the **View Log Files** or **View Raw Log** button. You should be able to see log messages confirming that data was deleted, created, and read.

You can also connect to the database and see the data in the `programming_language` table:

```shell
mariadb-shell --dsn mariadb://user:'Password123!'@127.0.0.1
```

Run the following query:

```SQL
SELECT * FROM demo.programming_languages;
```
