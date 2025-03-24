package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.port.out.persistence.account.AccountRepository;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponseMother;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import com.caelcs.model.account.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.MultiValuePattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.MDC;

import java.util.function.Consumer;

import static com.caelcs.adapter.out.rest.MDCClientRequestFilter.CORRELATION_ID;
import static com.caelcs.adapter.out.rest.MDCClientRequestFilter.X_CORRELATION_ID;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;

@QuarkusTest
@ConnectWireMock
@SuppressFBWarnings(value = {"UwF", "NP"}, justification = "This list is safely managed elsewhere")
class AccountResourceIntegrationTest {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    AccountRepository accountRepository;

    WireMock wireMock;

    @Test
    void test_get_account_endpoint_success() throws JsonProcessingException {
        //Given
        Account account = AccountMother.base();
        executeQuery(account1 -> accountRepository.saveEntity(account1), account);

        //And
        TransactionsResponse expectedTransactionsResponse = TransactionsResponseMother.base();
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(account.accountNumber()))
                .withQueryParam("accountType", equalTo(account.accountType().name()))
                .willReturn(aResponse()
                        .withStatus(OK.getStatusCode())
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(expectedTransactionsResponse))));

        //When
        AccountWebModel result = given()
                .queryParam("accountNumber", account.accountNumber())
                .queryParam("accountType", account.accountType())
                .when()
                .get("/accounts")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()) // Expect HTTP 201 Created
                .extract()
                .response().as(AccountWebModel.class);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(account.accountNumber(), result.accountNumber());
        Assertions.assertEquals(account.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
        Assertions.assertEquals(1, result.transactions().size());
    }

    @Test
    void test_get_account_GivenCorrelationIdIsGenerated_thenSuccess() throws JsonProcessingException {
        //Given
        Account account = AccountMother.base();
        executeQuery(account1 -> accountRepository.saveEntity(account1), account);

        //And
        TransactionsResponse expectedTransactionsResponse = TransactionsResponseMother.base();
        wireMock.register(get(urlPathEqualTo("/transactions"))
                .withQueryParam("accountNumber", equalTo(account.accountNumber()))
                .withQueryParam("accountType", equalTo(account.accountType().name()))
                .withHeader(X_CORRELATION_ID, matching(".*"))
                .willReturn(aResponse()
                        .withStatus(OK.getStatusCode())
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(expectedTransactionsResponse))));

        //When
        AccountWebModel result = given()
                .queryParam("accountNumber", account.accountNumber())
                .queryParam("accountType", account.accountType())
                .when()
                .get("/accounts")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()) // Expect HTTP 201 Created
                .extract()
                .response().as(AccountWebModel.class);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(account.accountNumber(), result.accountNumber());
        Assertions.assertEquals(account.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
        Assertions.assertEquals(1, result.transactions().size());
    }

    @Test
    void test_get_account_givenNoAccount_then404() {
        //Given
        Account account = AccountMother.base("99076323434", AccountType.CREDIT);

        //When and Then
        given()
                .queryParam("accountNumber", account.accountNumber())
                .queryParam("accountType", account.accountType())
                .when()
                .get("/accounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({",DEBIT", "32323232,", ",", "'',DEBIT"})
    void test_create_accounts_endpoint_whenInvalidValuesInPayload_thenFailed(String accountNumber, AccountType accountType) {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base(accountNumber, accountType);

        //When
        given()
                .contentType(ContentType.JSON)
                .body(accountCreateWebModel)
                .when()
                .post("/accounts")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void test_create_account_endpoint_success() {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //When
        AccountWebModel result = given()
                .body(accountCreateWebModel)
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

    @Transactional
    public void executeQuery(Consumer<Account> saveOperation, Account account) {
        saveOperation.accept(account);
    }


}