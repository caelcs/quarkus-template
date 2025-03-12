package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;

public interface TransactionsClient {

    TransactionsResponse getTransactionsByAccountNumberAndType(String accountNumber, AccountType accountType);

}
