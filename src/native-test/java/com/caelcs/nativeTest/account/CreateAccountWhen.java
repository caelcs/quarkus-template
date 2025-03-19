package com.caelcs.nativeTest.account;

import com.caelcs.adapter.in.rest.account.AccountWebModel;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class CreateAccountWhen extends Stage<CreateAccountWhen> {

    @ExpectedScenarioState
    private SharedAccountState sharedAccountState;

    CreateAccountWhen call_account_api_to_create() {
        sharedAccountState.setResponse(sharedAccountState.getAccounts().stream()
                .map(it ->
                        RestAssured.given()
                                .body(it)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .when()
                                .post("/accounts")
                                .then()
                                .statusCode(Response.Status.CREATED.getStatusCode())
                                .extract()
                                .response().as(AccountWebModel.class))
                .toList());

        return self();
    }
}
