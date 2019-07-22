package com.shafikul.money.transfer.model;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    @InjectMocks
    private Account account = new Account();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void accountTest() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void testWithDrawFromAccount() {
        account.sendToAccount(new BigDecimal(5.0));
        assertTrue(account.withDrawFromAccount(new BigDecimal(1.0)));
        assertFalse(account.withDrawFromAccount(new BigDecimal(10.0)));
    }

    @Test
    public void testSendToAccount() {
        assertTrue(account.sendToAccount(new BigDecimal(1.0)));
    }
}
