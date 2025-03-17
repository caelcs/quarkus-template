package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.dto.AccountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountCreateWebModelTest {

    @Test
    void test_toAccountDTO_success() {
        //Given
        AccountCreateWebModel accountCreateWebModel = AccountCreateWebModelMother.base();

        //When
        AccountDTO result = accountCreateWebModel.toAccountDTO();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateWebModel.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateWebModel.accountType(), result.accountType());
    }

}