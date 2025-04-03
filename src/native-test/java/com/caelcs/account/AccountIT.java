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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
    @MethodSource("provideAllowedCredentials")
    void test_GetAccount_GivenACreatedAccount_thenShouldGetSuccessfully(String username, String password) throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String accessToken = getAccessToken(username, password);

        //And
        createAccount(accountCreateWebModel, accessToken);

        //When
        AccountWebModel result = given()
                .auth().oauth2(accessToken)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .queryParam("accountNumber", accountCreateWebModel.accountNumber())
                .queryParam("accountType", accountCreateWebModel.accountType())
                .when()
                .get("/accounts")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .response().as(AccountWebModel.class);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateWebModel.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateWebModel.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
    }

    private static void createAccount(AccountCreateWebModel accountCreateWebModel, String accessToken) throws JsonProcessingException {
        String body = new ObjectMapper().writeValueAsString(accountCreateWebModel);
        given()
                .auth().oauth2(accessToken)
                .body(body)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode()) // Expect HTTP 201 Created
                .extract()
                .response().as(AccountWebModel.class);
    }


    private static Stream<Arguments> provideAllowedCredentials() {
        return allowedCredentials();
    }
}
