package com.caelcs.application.service.account.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.application.port.out.persistence.AccountRepository;
import com.caelcs.model.account.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class AccountService implements CreateAccountUseCase {

    private AccountRepository accountRepository;

    @Override
    public Account create(AccountDTO accountDTO) {
        accountRepository.saveEntity(accountDTO.toAccount());
        return accountRepository.findEntityByAccountNumberAndType(accountDTO.accountNumber(), accountDTO.accountType())
                .orElseThrow(() -> new EntityNotFoundException("Account Entity Not found"));
    }
}
