package com.spring.learning.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JdbcConnectionTest implements CommandLineRunner {

    private final DataSource dataSource;

    public JdbcConnectionTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connected through Spring DataSource!");
            System.out.println(connection.getMetaData().getURL());
        }
    }
}