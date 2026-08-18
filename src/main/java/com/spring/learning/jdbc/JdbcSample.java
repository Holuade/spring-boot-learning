package com.spring.learning.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.spring.learning.rest.dto.GreetingResponse;

public class JdbcSample {
    public static void main(String[] args) {
        String createConnection = "jdbc:h2:mem:testdb";

        try (Connection connection = DriverManager.getConnection(createConnection)) {
            System.out.println("Connected to H2!");

            String createTable = """
                    CREATE TABLE greetings (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255),
                        message VARCHAR(255)
                    )
                    """;

            try (PreparedStatement statement = connection.prepareStatement(createTable)) {
                statement.executeUpdate();
                System.out.println("Table created!");

            }

            String insertSql = """
                    INSERT INTO greetings (id, name, message)
                    VALUES(?, ?, ?)
                    """;
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

                insertStatement.setLong(1, 1L);
                insertStatement.setString(2, "Olu");
                insertStatement.setString(3, "Welcome to JDBC session!");
                insertStatement.executeUpdate();

                insertStatement.setLong(1, 2L);
                insertStatement.setString(2, "Jon Snow");
                insertStatement.setString(3, "The King in the North");
                insertStatement.executeUpdate();

                insertStatement.setLong(1, 3L);
                insertStatement.setString(2, "Ser Seaworth Davos");
                insertStatement.setString(3, "Stannis is the only true king");
                insertStatement.executeUpdate();
            }

            String selectSql = """
                    SELECT * from greetings
                    """;

            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        String message = resultSet.getString("message");
                        GreetingResponse greeting = new GreetingResponse(id, name, message);
                        System.out.println(greeting);
                    }
                }
            }

            String updateSql = """
                    UPDATE greetings
                    SET name = ?, message = ?
                    WHERE id = ?
                    """;
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setString(1, "Jon Targaryen");
                updateStatement.setString(2, "The North Remembers");
                updateStatement.setLong(3, 2L);

                int rowsUpdated = updateStatement.executeUpdate();
                System.out.println("Rows updated: " + rowsUpdated);
            }
            String selectSqlForUpdate = """
                    SELECT * FROM greetings
                    """;

            try (PreparedStatement selectStatement = connection.prepareStatement(selectSqlForUpdate)) {

                try (ResultSet resultSet = selectStatement.executeQuery()) {

                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        String message = resultSet.getString("message");

                        GreetingResponse greeting = new GreetingResponse(id, name, message);

                        System.out.println(greeting);
                    }
                }
            }

            String deleteSql = """
                    DELETE FROM greetings
                    WHERE id = ?
                    """;

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {

                deleteStatement.setLong(1, 3L);

                int rowsDeleted = deleteStatement.executeUpdate();

                System.out.println("Rows deleted: " + rowsDeleted);
            }

            String selectSqlForDelete = """
                    SELECT * FROM greetings
                    """;

            try (PreparedStatement selectStatement = connection.prepareStatement(selectSqlForDelete)) {

                try (ResultSet resultSet = selectStatement.executeQuery()) {

                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        String message = resultSet.getString("message");

                        GreetingResponse greeting = new GreetingResponse(id, name, message);

                        System.out.println(greeting);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
