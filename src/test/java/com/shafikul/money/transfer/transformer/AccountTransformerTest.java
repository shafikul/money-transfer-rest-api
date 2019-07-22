package com.shafikul.money.transfer.transformer;

import com.shafikul.money.transfer.core.dto.AccountDto;
import com.shafikul.money.transfer.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class AccountTransformerTest {

    @InjectMocks
    private AccountTransformer accountTransformer;

    @Mock
    private Account account;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void teatTransform() {
        account = new Account();
        account.setName("Mango");
        account.setId(1);
        AccountDto accountDto = accountTransformer.getInstance().transform(account);
        Assert.assertTrue(accountDto.getBalance().compareTo(BigDecimal.ZERO) == 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTransformBack() {
        accountTransformer.transformBack(new AccountDto());
    }
}
