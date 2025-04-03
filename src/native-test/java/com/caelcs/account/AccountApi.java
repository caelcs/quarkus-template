package com.caelcs.account;

import com.caelcs.adapter.in.rest.account.AccountWebModel;
import com.caelcs.model.account.AccountType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;

public class AccountApi {

    public static AccountWebModel createAccount(String body, String accessToken) {
        return given()
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


    public static AccountWebModel getAccount(String accountNumber, AccountType accountType, String accessToken) {
        return given()
            .auth().oauth2(accessToken)
            .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .queryParam("accountNumber", accountNumber)
            .queryParam("accountType", accountType)
            .when()
            .get("/accounts")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .response().as(AccountWebModel.class);
    }

}
