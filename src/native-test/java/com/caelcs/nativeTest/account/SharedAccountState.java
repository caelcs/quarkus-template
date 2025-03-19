package com.caelcs.nativeTest.account;

import com.caelcs.adapter.in.rest.account.AccountWebModel;
import com.caelcs.application.dto.AccountDTO;

import java.util.List;

public class SharedAccountState {

    private List<AccountDTO> accounts;
    private List<AccountWebModel> response;

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public List<AccountWebModel> getResponse() {
        return response;
    }

    public void setResponse(List<AccountWebModel> response) {
        this.response = response;
    }
}
