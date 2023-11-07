# Quick Start: Java and MariaDB

[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)

This repository contains examples on how to connect to [MariaDB](https://mariadb.com) databases using a combination of different Java libraries and frameworks.

## Before you run the examples

1. Make sure you have a MariaDB Server ([Enterprise](https://mariadb.com/products/enterprise/) or [Community](https://mariadb.com/products/community-server/)) running. If you don't have a MariaDB server running, you can easily run one using [Docker](https://hub.docker.com/u/mariadb):

```Shell
docker run --name mariadb --detach --publish 3306:3306 --env MARIADB_ROOT_PASSWORD='RootPassword123!' mariadb
```

Alternatively, you can [Download](https://mariadb.com/downloads/) and install the server directly on your OS.

2. Connect to the database using [MariaDB Shell](https://mariadb.com/downloads/tools/shell/):

```shell
mariadb-shell --dsn mariadb://root:'RootPassword123!'@127.0.0.1
```

Alternatively, you can use any database client compatible with MariaDB.

3. Prepare the database schema and user as follows:

```sql
CREATE DATABASE demo;
CREATE USER 'user'@'%' IDENTIFIED BY 'Password123!';
GRANT SELECT, INSERT, UPDATE, DELETE, DROP ON demo.* TO 'user'@'%';

CREATE TABLE demo.programming_language(
	pl_id INT PRIMARY KEY AUTO_INCREMENT,
	pl_name VARCHAR(50) NOT NULL UNIQUE,
	pl_rating INT
);
```

## JDBC & JPA

- [JDBC (Java Database Connectivity)](jdbc/): The foundational technology used for persistence in Java.
- [JPA/Hibernate](jpa-hibernate/): The de-facto standard for consuming databases from Java apps.

## Spring Boot

- [Spring Boot Data JPA](spring-boot-jpa/): Spring-based programming model for data access on top of JPA.
- [R2DBC ➚](https://github.com/mariadb-developers/reactive-programming-java-examples): Reactive database connectivity.
- [jOOQ](spring-boot-jooq/): Type-safe SQL queries in Java.
- [MyBatis](spring-boot-mybatis/): Map SQL results to Java methods in a simple way.

## Jakarta EE (Java EE)

- [Jakarta EE + GlassFish](jakarta-ee/): Jakarta EE is set of vendor-neutral specifications to build enterprise Java applications.
- [MicroProfile + Open Liberty](microprofile/): An open-source community specification for Enterprise Java microservices.


## Quarkus

(work in progress)