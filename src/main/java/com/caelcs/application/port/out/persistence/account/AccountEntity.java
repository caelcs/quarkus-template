package com.caelcs.application.port.out.persistence.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.persistence.*;
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
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
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
