# JDBC Quickstart - Part 1

This Maven project shows the minimal configuration needed to connect to
MariaDB databases using Java Database Connectivity (JDBC) without any
other persistence frameworks.

## Requirements

- Java 17 or later. Previous versions should work (update the version 
in the **pom.xml** file).
- [Apache Maven](https://maven.apache.org).
- MariaDB server. If you don't want to install
anything extra, try creating a
[free SkySQL account](https://mariadb.com/products/skysql)).
- An SQL client tool like `mariadb`, DBeaver, or a SQL integration for
your IDE. Execute the following SQL sentence: `CREATE DATABASE jdbc_demo;`.

## Running the app

Run the following form the command line:

```
git clone git@github.com:mariadb-developers/java-quickstart.git
cd java-quickstart/jdbc/part1/
mvn package
java -jar  java -jar target/jdbc-demo-1.0-SNAPSHOT.jar
```

## Helpful Resources

Read the tutorial (available soon) or watch the video for detailed steps
on how to implement this application from scratch.

[![JDBC Tutorial part 1/3 – Database connections](https://img.youtube.com/vi/ceAev_93p3s/hqdefault.jpg)](https://www.youtube.com/watch?v=ceAev_93p3s)

* [Official MariaDB Documentation](https://mariadb.com/docs)
* [MariaDB Quickstart Guide](https://github.com/mariadb-developers/mariadb-getting-started)

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

## License <a name="license"></a>
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)
