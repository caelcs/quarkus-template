package com.caelcs.account;

import com.caelcs.TestContainerLifecycleManager;
import com.caelcs.adapter.in.rest.account.AccountCreateWebModel;
import com.caelcs.adapter.in.rest.account.AccountCreateWebModelMother;
import com.caelcs.adapter.in.rest.account.AccountWebModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.caelcs.auth.AuthAccessTokenUtil.*;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;

@QuarkusIntegrationTest
@QuarkusTestResource(TestContainerLifecycleManager.class)
public class AccountIT {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void objectMapperInstance() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @ParameterizedTest
    @CsvSource({"alice, alice", "pinza, pinza"})
    void testAccountCreation(String username, String password) throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String accessToken = getAccessToken(username, password);
        System.out.println("✅ accessToken: " + accessToken);

        //When
        String body = new ObjectMapper().writeValueAsString(accountCreateWebModel);
        System.out.println("✅ body: " + body);
        AccountWebModel result = given()
                .auth().oauth2(accessToken)
                .body(body)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode()) // Expect HTTP 201 Created
                .extract()
                .response().as(AccountWebModel.class);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateWebModel.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateWebModel.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
    }

    @Test
    void testAccountCreation_givenUserDoesNotExists_then401Unauthorised() throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String invalidAccessToken = getInvalidAccessToken();
        System.out.println("✅ Invalid AccessToken: " + invalidAccessToken);

        //When
        String body = objectMapper.writeValueAsString(accountCreateWebModel);
        System.out.println("✅ body: " + body);

        given()
                .auth().oauth2(invalidAccessToken)
                .body(body)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testAccountCreation_givenExpiredAccessToken_then401Unauthorised() throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String expiredAccessToken = getExpiredAccessToken();
        System.out.println("✅ Invalid AccessToken: " + expiredAccessToken);

        //When
        String body = objectMapper.writeValueAsString(accountCreateWebModel);
        System.out.println("✅ body: " + body);

        given()
                .auth().oauth2(expiredAccessToken)
                .body(body)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({"john, jhon", "linda, linda", "lucas, lucas"})
    void testAccountCreation_givenValidAccessTokenWithWrongRole_then401Unauthorised(String username, String password) throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String accessToken = getAccessToken(username, password);
        System.out.println("✅ AccessToken: " + accessToken);

        //When
        String body = objectMapper.writeValueAsString(accountCreateWebModel);
        System.out.println("✅ body: " + body);

        given()
                .auth().oauth2(accessToken)
                .body(body)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }
}
