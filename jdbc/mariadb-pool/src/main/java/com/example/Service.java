package com.example;

import org.mariadb.jdbc.MariaDbDataSource;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * You can configure the database connection in the constructor.
 * You need an SQL database with the following table:
 *
 * <pre>
 * CREATE TABLE programming_language(
 *     name VARCHAR(50) NOT NULL UNIQUE,
 *     Rating INT
 * );
 * </pre>
 *
 * See the comments in the constructor for more instructions.
 *
 */
public class Service {

	private static Service instance;

	private DataSource dataSource;

	private Service() throws SQLException {
		// First try with a DataSource without pooling:
		MariaDbDataSource dataSource = new MariaDbDataSource();
		/*
		 * That should fail (SQLException: too many connections)
		 * Try now commenting the previous executable line
		 * and using the following DataSource that supports pooling:
		 * MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();
		 * That should work!
		 */
		dataSource.setUrl("jdbc:mariadb://localhost:3306/demo");
		dataSource.setUser("user");
		dataSource.setPassword("password");
		this.dataSource = dataSource;
		/*
		 * If you are using MariaDB SkySQL (https://mariadb.com/products/skysql),
		 * enable SSL and specify the path to the CA chain file that you can download
		 * from the SkySQL Portal (https://cloud.mariadb.com):
		 * jdbc:mariadb://demo-db0000xxxx.mdb000xxxx.db.skysql.net:5047/demo?useSsl=true&
		 * serverSslCert=/path/to/your/skysql_chain.pem
		 */
	}

	public static synchronized Service getInstance() throws SQLException {
		return instance == null ? instance = new Service() : instance;
	}

	public List<String> getAllProgrammingLanguages() throws SQLException {
		var list = new ArrayList<String>();
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"SELECT name FROM programming_language")) {
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					list.add(resultSet.getString("name"));
				}
			}
		}
		return list;
	}

}
