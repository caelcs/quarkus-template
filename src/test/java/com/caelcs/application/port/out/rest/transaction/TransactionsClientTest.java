package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.UUID;

import static com.caelcs.adapter.out.rest.MDCClientRequestFilter.X_CORRELATION_ID;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static org.jboss.resteasy.reactive.RestResponse.Status.NOT_FOUND;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;

@QuarkusTest
@ConnectWireMock
@SuppressFBWarnings(value = {"UwF", "NP"}, justification = "This list is safely managed elsewhere")
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
        String expectedCorrelationId = UUID.randomUUID().toString();
        MDC.put("correlationId", expectedCorrelationId);

        //And
        TransactionsResponse expectedTransactionsResponse = TransactionsResponseMother.base();
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .withHeader(X_CORRELATION_ID, equalTo(expectedCorrelationId))
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
    void test_getTransactionsByAccountNumberAndType_GivenNonExiting_thenNotFound() {
        //Given
        String accountNumber = "3343456323";
        AccountType accountType = AccountType.DEBIT;

        //And
        String expectedCorrelationId = UUID.randomUUID().toString();
        MDC.put("correlationId", expectedCorrelationId);

        //And
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(accountNumber))
                .withQueryParam("accountType", equalTo(accountType.name()))
                .withHeader(X_CORRELATION_ID, equalTo(expectedCorrelationId))
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