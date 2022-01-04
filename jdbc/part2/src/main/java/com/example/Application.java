package com.example;

import java.sql.*;

public class Application {

    private static Connection connection;

    /**
     * Entry point of the application. Opens and closes the database connection
     * and performs CRUD operations against the database. You need an SQL database
     * with the following table:
     *
     * <pre>
     * CREATE TABLE programming_language(
     *     name VARCHAR(50) NOT NULL UNIQUE,
     *     Rating INT
     * );
     * </pre>
     *
     * @param args (not used)
     * @throws SQLException if an error occurs when interacting with the database
     */
    public static void main(String[] args) throws SQLException {
        try {
            initDatabaseConnection();
            deleteData("%");
            readData();
            createData("Java", 10);
            createData("JavaScript", 9);
            createData("C++", 8);
            readData();
            updateData("C++", 7);
            readData();
            deleteData("C++");
            readData();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void createData(String name, int rating) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO programming_language(name, rating)
                    VALUES (?, ?)
                """)) {
            statement.setString(1, name);
            statement.setInt(2, rating);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }

    private static void readData() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT name, rating
                    FROM programming_language
                    ORDER BY rating DESC
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                while (resultSet.next()) {
                    empty = false;
                    String name = resultSet.getString("name");
                    int rating = resultSet.getInt("rating");
                    System.out.println("\t> " + name + ": " + rating);
                }
                if (empty) {
                    System.out.println("\t (no data)");
                }
            }
        }
    }

    private static void updateData(String name, int newRating) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE programming_language
                    SET rating = ?
                    WHERE name = ?
                """)) {
            statement.setInt(1, newRating);
            statement.setString(2, name);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        }
    }

    private static void deleteData(String nameExpression) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM programming_language
                    WHERE name LIKE ?
                """)) {
            statement.setString(1, nameExpression);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        }
    }

    private static void initDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/jdbc_demo",
                "user", "password"
        );
        System.out.println("Connection valid: " + connection.isValid(5));
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection...");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }
}
