package com.caelcs.application.service.account.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.model.account.Account;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountService implements CreateAccountUseCase {

    @Override
    public Account create(AccountDTO accountDTO) {
        Account result = accountDTO.toAccount();
        return result;
    }
}
