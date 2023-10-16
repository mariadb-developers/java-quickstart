# MicroProfile / Open Liberty quickstart

[MicroProfile](https://microprofile.io) is an open-source community specification for Enterprise Java microservices.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA in a MicroProfile/Jakarta EE (web profile) application deployed to [Open Liberty](https://openliberty.io).

## LT;DR (kinda)

Add the MicroProfile and Jakarta EE web profile APIs:

```xml
<dependency>
	<groupId>org.eclipse.microprofile</groupId>
	<artifactId>microprofile</artifactId>
	<version>5.0</version>
	<type>pom</type>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>jakarta.platform</groupId>
	<artifactId>jakarta.jakartaee-web-api</artifactId>
	<version>9.1.0</version>
	<scope>provided</scope>
</dependency>
```

Define a Persistence Unit:

```xml
<persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="2.0">
	<persistence-unit name="microprofile-demo" transaction-type="JTA">
	<jta-data-source>jdbc/mariadb-database</jta-data-source>
		<exclude-unlisted-classes>false</exclude-unlisted-classes>
		<properties>
			<property name="eclipselink.target-database" value="MySQL"/> <!-- EclipseLink JPA (default JPA implementation in Glassfish) requires this -->
		</properties>
	</persistence-unit>
</persistence>
```

Add the Liberty Maven plug-in in the **pom.xml** file:

```xml
<plugin>
	<groupId>io.openliberty.tools</groupId>
	<artifactId>liberty-maven-plugin</artifactId>
	<version>3.5.1</version>
	<executions>
		<execution>
			<id>package-server</id>
			<phase>package</phase>
			<goals>
				<goal>create</goal>
				<goal>install-feature</goal>
				<goal>deploy</goal>
				<goal>package</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<include>runnable</include>
		<bootstrapProperties>
			<com.ibm.ws.logging.console.log.level>info</com.ibm.ws.logging.console.log.level>
		</bootstrapProperties>
		<copyDependencies>
			<dependencyGroup>
				<location>jdbc</location>
				<dependency>
					<groupId>org.mariadb.jdbc</groupId>
					<artifactId>mariadb-java-client</artifactId>
					<version>3.0.4</version>
				</dependency>
			</dependencyGroup>
		</copyDependencies>
	</configuration>
</plugin>
```

Add the MicroProfile and Jakarta EE web profile features in the **server.xml** file:

```xml
<featureManager>
	<feature>microProfile-5.0</feature>
	<feature>webProfile-9.1</feature>
</featureManager>
```

Configure the database connection in the **server.xml** file:

```xml
<library id="jdbcLib">
	<fileset dir="jdbc" includes="*.jar"/>
</library>

<dataSource id="DefaultDataSource" jndiName="jdbc/mariadb-database" type="java.sql.Driver">
	<jdbcDriver libraryRef="jdbcLib"/>
	<properties
		URL="jdbc:mariadb://localhost:3306/microprofile_demo"
		user="user"
		password="Password123!"
	/>
</dataSource>
```

> If you are using [MariaDB SkySQL](https://mariadb.com/products/skysql/), enable SSL and specify the path to the CA chain file that you can download from the [SkySQL Portal](https://cloud.mariadb.com):
> 
> `jdbc:mariadb://demo-db0000xxxx.mdb000xxxx.db.skysql.net:5047/microprofile_demo?sslMode=verify-ca&serverSslCert=/path/to/your/skysql_chain.pem`

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
@PersistenceContext(unitName = "microprofile-demo")
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
CREATE DATABASE microprofile_demo;
CREATE USER 'user'@'%';
GRANT ALL ON microprofile_demo.* TO 'user'@'%' IDENTIFIED BY 'password';


USE microprofile_demo;
CREATE TABLE programming_language(
	pl_id INT PRIMARY KEY AUTO_INCREMENT,
	pl_name VARCHAR(50) NOT NULL UNIQUE,
	pl_rating INT
);
```

Build the application and run it:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/microprofile/
mvn package
java -jar target/microprofile-1.0-SNAPSHOT.jar
```

You should be able to see new rows in the `programming_language` table in the database as well as log messages confirming that data was deleted, created, and read.

Alternatively, you can start the application in development mode which picks up the changes yo make to the code and deploys them automatically:

```
mvn liberty:dev
```