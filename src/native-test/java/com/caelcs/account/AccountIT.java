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
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;

@QuarkusIntegrationTest
@QuarkusTestResource(TestContainerLifecycleManager.class)
public class AccountIT {

    @Test
    void testAccountCreation() throws JsonProcessingException {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //And
        String accessToken = getAccessToken("alice", "alice");
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

    private String getAccessToken(String username, String password) {
        String authServerUrl = TestContainerLifecycleManager.keycloakContainer.getAuthServerUrl();
        System.out.println("✅ authurl: " + authServerUrl);
        return given()
                .relaxedHTTPSValidation()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .auth().preemptive().basic("quarkus-template-app", "YSFwvyazqPmLukTvwBWa0ZhlhtP3T031")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(authServerUrl + "/realms/quarkus-template/protocol/openid-connect/token")
                .then()
                .statusCode(200)
                .extract().path("access_token");
    }
}
