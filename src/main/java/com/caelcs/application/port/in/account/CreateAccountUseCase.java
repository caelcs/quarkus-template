package com.caelcs.application.port.in.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.Account;

public interface CreateAccountUseCase {

    Account create(AccountDTO accountDTO);

}
