package com.caelcs.adapter.out.persistence;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "accounts")
public class AccountEntity {

    @Id
    private UUID id;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE")
    private AccountType accountType;

    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    public static AccountEntity fromAccount(Account account) {
        return AccountEntityMapper.INSTANCE.fromAccount(account);
    }

    public static Account toAccount(AccountEntity accountEntity) {
        return AccountEntityMapper.INSTANCE.toAccount(accountEntity);
    }
}
