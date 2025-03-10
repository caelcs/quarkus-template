package com.caelcs.adapter.out.persistence;

import com.caelcs.application.port.out.persistence.AccountRepository;
import com.caelcs.model.account.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AccountRepositoryDefault implements AccountRepository, PanacheRepositoryBase<AccountEntity, UUID> {

    @Override
    public void saveEntity(Account account) {
        persist(AccountEntity.fromAccount(account));
    }

}
