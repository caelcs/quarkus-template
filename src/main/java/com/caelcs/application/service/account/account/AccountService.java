package com.caelcs.application.service.account.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.application.port.out.persistence.AccountRepository;
import com.caelcs.model.account.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class AccountService implements CreateAccountUseCase {

    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Account create(AccountDTO accountDTO) {
        Account account = accountDTO.toAccount()
                .toBuilder()
                .id(UUID.randomUUID())
                .build();
        accountRepository.saveEntity(account);
        return accountRepository.findEntityByAccountNumberAndType(accountDTO.accountNumber(), accountDTO.accountType())
                .orElseThrow(() -> new EntityNotFoundException("Account Entity Not found"));
    }
}
