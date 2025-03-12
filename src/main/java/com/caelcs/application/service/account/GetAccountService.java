package com.caelcs.application.service.account;

import com.caelcs.application.port.in.account.GetAccountUseCase;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class GetAccountService implements GetAccountUseCase {
    @Override
    public Optional<Account> getAccountByAccountNumberAndType(String accountNumber, AccountType accountType) {
        return Optional.empty();
    }
}
