package com.caelcs.adapter.out.persistence;

import com.caelcs.application.port.out.persistence.AccountRepository;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepositoryDefault implements AccountRepository, PanacheRepositoryBase<AccountEntity, UUID> {

    @Override
    public void saveEntity(Account account) {
        persist(AccountEntity.fromAccount(account));
    }

    @Override
    public Optional<Account> findEntityByAccountNumberAndType(String accountNumber, AccountType accountType) {
        return find("accountNumber = ?1 and accountType = ?2", accountNumber, accountType).firstResultOptional().map(AccountEntity::toAccount);
    }
}
