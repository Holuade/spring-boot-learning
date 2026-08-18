package com.spring.learning.rest.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;

@Repository
public class JdbcGreetingRepository implements GreetingRepository {

    private final DataSource dataSource;

    public JdbcGreetingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<GreetingResponse> findById(Long id) {

        String sql = """
                SELECT id, name, message
                FROM greetings
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    long greetingId = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String message = resultSet.getString("message");

                    GreetingResponse greeting = new GreetingResponse(greetingId, name, message);

                    return Optional.of(greeting);
                }

                return Optional.empty();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GreetingResponse> findAll() {

        String sql = """
                SELECT id, name, message
                FROM greetings
                """;

        List<GreetingResponse> greetings = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String message = resultSet.getString("message");

                GreetingResponse greeting = new GreetingResponse(id, name, message);

                greetings.add(greeting);
            }

            return greetings;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GreetingResponse> findByName(String name) {
        String sql = """
                SELECT id, name, message
                FROM greetings
                WHERE name = ?
                """;

        List<GreetingResponse> greetings = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long greetingId = resultSet.getLong("id");
                    String greetingName = resultSet.getString("name");
                    String message = resultSet.getString("message");

                    GreetingResponse greeting = new GreetingResponse(greetingId, greetingName, message);
                    greetings.add(greeting);
                }
            }

            return greetings;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean deleteById(Long id) {

        String sql = """
                DELETE FROM greetings
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<GreetingResponse> update(Long id, GreetingRequest request) {

        String sql = """
                UPDATE greetings
                SET name = ?, message = ?
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, request.name());
            statement.setString(2, request.message());
            statement.setLong(3, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                return Optional.empty();
            }

            return findById(id);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public GreetingResponse create(GreetingRequest request) {

        String sql = """
                INSERT INTO greetings (name, message)
                VALUES (?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, request.name());
            statement.setString(2, request.message());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Creating greeting failed.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    long id = generatedKeys.getLong(1);

                    return new GreetingResponse(
                            id,
                            request.name(),
                            request.message());
                }

                throw new SQLException("Creating greeting failed: no ID obtained.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}