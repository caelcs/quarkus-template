package com.caelcs;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class TestContainerLifecycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Override
    public Map<String, String> start() {
        postgreSQLContainer.start();
        waitForDatabase();
        return Map.of("quarkus.datasource.native-test.jdbc.url", postgreSQLContainer.getJdbcUrl());
    }

    @Override
    public void stop() {
        postgreSQLContainer.stop();
    }

    private static void waitForDatabase() {
        try (var connection = postgreSQLContainer.createConnection("")) {
            try (var statement = connection.createStatement()) {
                statement.execute("SELECT 1");
                System.out.println("✅ Database is ready!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Database did not become ready in time!", e);
        }
    }
}