package com.caelcs.nativeTest.account;

import com.caelcs.adapter.in.rest.account.AccountWebModel;
import com.caelcs.application.dto.AccountDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

import java.util.List;

@SuppressFBWarnings(value = {"EI2", "EI", "UwF", "NP"}, justification = "This list is safely managed elsewhere")
@Data
public class SharedAccountState {

    private List<AccountDTO> accounts;
    private List<AccountWebModel> response;

}
