package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.application.port.out.rest.transaction.TransactionResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponseMother;
import com.caelcs.model.account.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@ConnectWireMock
class TransactionResourceIntegrationTest {

    WireMock wireMock;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private TransactionResource resource;

    @Test
    void test_get_transactions_endpoint_success() throws JsonProcessingException {
        //Given
        String accountNumber = "232443354";
        AccountType accountType = AccountType.CREDIT;

        //And
        TransactionsResponse expectedTransactionsResponse = TransactionsResponseMother.base();
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(expectedTransactionsResponse))));

        //When
        TransactionsWebModel result = given()
                .queryParam("accountNumber", accountNumber)
                .queryParam("accountType", accountType.name())
                .when()
                .get("/transactions")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .response().as(TransactionsWebModel.class);

        //Then
        assertNotNull(result);
        assertFalse(result.transactions().isEmpty());
        assertTransactionsWebModel(result, expectedTransactionsResponse);
    }

    @Test
    void test_get_transactions_whenNoTransactionsWith404_thenStatusCode200WithEmptyTransactions() {
        //Given
        String accountNumber = "232443354";
        AccountType accountType = AccountType.CREDIT;

        //And
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .willReturn(aResponse()
                        .withStatus(404)));

        //When
        TransactionsWebModel result = given()
                .queryParam("accountNumber", accountNumber)
                .queryParam("accountType", accountType.name())
                .when()
                .get("/transactions")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .response().as(TransactionsWebModel.class);

        //Then
        assertNotNull(result);
        assertNotNull(result.transactions());
        assertTrue(result.transactions().isEmpty());
    }

    @Test
    void test_get_transactions_whenInternalErrorWith500_thenStatusCode500() {
        //Given
        String accountNumber = "232443354";
        AccountType accountType = AccountType.CREDIT;

        //And
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .willReturn(aResponse()
                        .withStatus(500)));

        //When
        given()
                .queryParam("accountNumber", accountNumber)
                .queryParam("accountType", accountType.name())
                .when()
                .get("/transactions")
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    private static void assertTransactionsWebModel(TransactionsWebModel result, TransactionsResponse expectedTransactionsResponse) {
        TransactionWebModel actual = result.transactions().getFirst();
        TransactionResponse expected = expectedTransactionsResponse.transactions().getFirst();
        assertEquals(expected.creationDate(), actual.creationDate());
        assertEquals(expected.amount(), actual.amount());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.type(), actual.type());
    }

}