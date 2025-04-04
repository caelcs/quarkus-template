package com.caelcs.account;

import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class OIDCApi {

    public static final String CLIENT_ID = "quarkus-template-app";
    public static final String CLIENT_SECRET = "YSFwvyazqPmLukTvwBWa0ZhlhtP3T031";

    public static final String REALM = "quarkus-template";

    public static String getAccessToken(String username, String password) {
        String authServerUrl = System.getenv("KEYCLOAK_URL");
        return given()
                .relaxedHTTPSValidation()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET)
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, REALM))
                .then()
                .statusCode(200)
                .extract().path("access_token");
    }

}
