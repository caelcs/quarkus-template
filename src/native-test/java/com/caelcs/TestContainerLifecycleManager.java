package com.caelcs;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class TestContainerLifecycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    public static final KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:26.1.4")
            .withAdminUsername("admin")
            .withAdminPassword("admin")
            .withRealmImportFile("test-realm.json");

    @Override
    public Map<String, String> start() {
        postgreSQLContainer.start();
        keycloakContainer.start();
        waitForDatabaseIsReady();
        waitForKeycloakIsReady(keycloakContainer.getAuthServerUrl());
        return Map.of(
                "quarkus.datasource.jdbc.url", postgreSQLContainer.getJdbcUrl(),
                "quarkus.datasource.username", postgreSQLContainer.getUsername(),
                "quarkus.datasource.password", postgreSQLContainer.getPassword(),
                "keycloak.url", keycloakContainer.getAuthServerUrl(),
                "keycloak.default.client-id", "quarkus-template-app",
                "keycloak.default.client-secret", "thisisasecret",
                "keycloak.default.realm", "quarkus-template"
        );
    }

    @Override
    public void stop() {
        postgreSQLContainer.stop();
    }

    private static void waitForDatabaseIsReady() {
        try (var connection = postgreSQLContainer.createConnection("")) {
            try (var statement = connection.createStatement()) {
                statement.execute("SELECT 1");
                System.out.println("✅ Database is ready!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Database did not become ready in time!", e);
        }
    }

    private void waitForKeycloakIsReady(String keycloakUrl) {
        await().atMost(30, SECONDS).until(() -> {
            int statusCode = given()
                    .when().get(keycloakUrl + "/realms/quarkus-template")
                    .then().extract().statusCode();

            if (statusCode == 200) {
                System.out.println("✅ Keycloak is ready!");
                return true;
            }

            return false;
        });
    }
}