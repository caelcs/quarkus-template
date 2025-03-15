package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static org.jboss.resteasy.reactive.RestResponse.Status.NOT_FOUND;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;

@QuarkusTest
@ConnectWireMock
class TransactionsClientTest {

    WireMock wireMock;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    TransactionsClient client;

    @Test
    void test_getTransactionsByAccountNumberAndType_GivenValidAccount_thenListAllTransactionsSuccess() throws JsonProcessingException {
        //Given
        String accountNumber = "1343422563231";
        AccountType accountType = AccountType.DEBIT;

        //And
        TransactionsResponse expectedTransactionsResponse = TransactionsResponseMother.base();
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .willReturn(aResponse()
                        .withStatus(OK.getStatusCode())
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(expectedTransactionsResponse))));

        //When
        RestResponse<TransactionsResponse> result = client.getTransactionsByAccountNumberAndType(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        Assertions.assertEquals(1, result.getEntity().transactions().size());
        Assertions.assertTrue(result.getEntity().transactions().containsAll(expectedTransactionsResponse.transactions()));
    }

    @Test
    void test_getTransactionsByAccountNumberAndType_GivenNonExiting_thenNotFound() throws JsonProcessingException {
        //Given
        String accountNumber = "3343456323";
        AccountType accountType = AccountType.DEBIT;

        //And
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .willReturn(aResponse()
                        .withStatus(NOT_FOUND.getStatusCode())));

        //When
        RestResponse<TransactionsResponse> result = client.getTransactionsByAccountNumberAndType(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
        Assertions.assertNull(result.getEntity());
    }

}