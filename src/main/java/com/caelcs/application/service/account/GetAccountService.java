package com.caelcs.application.service.account;

import com.caelcs.application.port.in.account.GetAccountUseCase;
import com.caelcs.application.port.out.persistence.account.AccountEntity;
import com.caelcs.application.port.out.persistence.account.AccountRepository;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class GetAccountService implements GetAccountUseCase {

    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Optional<Account> getAccountByAccountNumberAndType(String accountNumber, AccountType accountType) {
        return accountRepository.findEntityByAccountNumberAndType(accountNumber, accountType)
                .map(AccountEntity::toAccount);
    }

}
