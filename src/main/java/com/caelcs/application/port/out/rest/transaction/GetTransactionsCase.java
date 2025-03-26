package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;

import java.util.List;

public interface GetTransactionsCase {

    List<Transaction> getTransactionsByAccount(String accountNumber, AccountType accountType);

}
