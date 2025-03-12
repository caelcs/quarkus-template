package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.AccountType;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

@QuarkusTest
class AccountResourceIntegrationTest {

    @Test
    void test_accounts_endpoint_success() {
        //Given
        AccountCreateRequest accountCreateRequest = AccountCreateRequestMother.base();

        //When
        AccountResponse result = given()
                .contentType(ContentType.JSON)
                .body(accountCreateRequest)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode()) // Expect HTTP 201 Created
                .extract()
                .response().as(AccountResponse.class);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateRequest.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateRequest.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
    }

    @ParameterizedTest
    @CsvSource({",DEBIT", "32323232,", ",", "'',DEBIT"})
    void test_accounts_endpoint_whenInvalidValuesInPayload_thenFailed(String accountNumber, AccountType accountType) {
        //Given
        AccountCreateRequest accountCreateRequest = AccountCreateRequestMother.base(accountNumber, accountType);

        //When
        given()
                .contentType(ContentType.JSON)
                .body(accountCreateRequest)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}