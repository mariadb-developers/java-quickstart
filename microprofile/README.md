# MicroProfile / Open Liberty quickstart

[MicroProfile](https://microprofile.io) is an open-source community specification for Enterprise Java microservices.

This Maven project shows the minimal configuration needed to connect to MariaDB databases using JPA in a MicroProfile/Jakarta EE (web profile) application deployed to [Open Liberty](https://openliberty.io).

## LT;DR (kinda)

Add the MicroProfile and Jakarta EE web profile APIs:

```xml
<dependency>
	<groupId>org.eclipse.microprofile</groupId>
	<artifactId>microprofile</artifactId>
	<version>LATEST</version>
	<type>pom</type>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>jakarta.platform</groupId>
	<artifactId>jakarta.jakartaee-web-api</artifactId>
	<version>LATEST</version>
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
	<version>LATEST</version>
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
					<version>LATEST</version>
				</dependency>
			</dependencyGroup>
		</copyDependencies>
	</configuration>
</plugin>
```

Add the MicroProfile and Jakarta EE web profile features in the **server.xml** file:

```xml
<featureManager>
	<feature>microProfile-LATEST</feature>
	<feature>webProfile-LATEST</feature>
</featureManager>
```

Configure the database connection in the **src/main/liberty/config/server.xml** file:

```xml
<library id="jdbcLib">
	<fileset dir="jdbc" includes="*.jar"/>
</library>

<dataSource id="DefaultDataSource" jndiName="jdbc/mariadb-database" type="java.sql.Driver">
	<jdbcDriver libraryRef="jdbcLib"/>
	<properties
		URL="jdbc:mariadb://localhost:3306/demo"
		user="user"
		password="Password123!"
	/>
</dataSource>
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
- Java 21 or later. Previous versions should work (update the version in the **pom.xml** file).
Apache Maven.
- Eclipse GlassFish 6.2.5 or later.
- MariaDB server.
- MariaDB Connector/J 3.2.0 or later.
- An SQL client tool like mariadb, DBeaver, or an SQL integration for your IDE.

## Preparing the database

See the instructions [here](../README.md).

## Running the app

Build the application and run it:

```Shell
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/microprofile/
mvn package
java -jar target/microprofile-1.0-SNAPSHOT.jar
```

Alternatively, you can start the application in development mode which picks up the changes yo make to the code and deploys them automatically:

```
mvn liberty:dev
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
