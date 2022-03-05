# JDBC Quickstart

Java Database Connectivity (JDBC) is an API that allows Java applications
use SQL databases. JDBC is the foundation technology used by persistence
frameworks such as JPA, Hibernate, MyBatis, and jOOQ.

This directory contains three independent Maven projects with the minimal
configuration required to get started with JDBC and MariaDB. Each project
showcases a specific feature of JDBC:

- [part1](part1/): Opening and closing database connections
- [part2](part2/): Executing SQL statements using JDBC
- [part3](part3/): Using connection pools (HikariCP)

This directory also contains an example that shows how to use the connection
pool included with the MariaDB JDBC driver:

- [mariadb-pool](mariadb-pool/): Using the `MariaDbPoolDataSource` class

## Running the apps

See the **README.md** files in each subdirectory to learn how to run the
examples.

## Support and Contribution

Please feel free to submit PR's, issues or requests to this project
directly.

If you have any other questions, comments, or looking for more information
on MariaDB please check out:

* [MariaDB Developer Hub](https://mariadb.com/developers)
* [MariaDB Community Slack](https://r.mariadb.com/join-community-slack)

Or reach out to us directly via:

* [developers@mariadb.com](mailto:developers@mariadb.com)
* [MariaDB Twitter](https://twitter.com/mariadb)
