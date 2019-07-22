package com.shafikul.money.transfer.repository;

import com.shafikul.money.transfer.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class AccountRepositoryTest {

    private AccountRepository accountRepository = AccountRepository.INSTANCE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOpenAccountIfNotExists() {
        accountRepository.openAccountIfNotExists(getMockAccountObject());
        Assert.assertTrue(accountRepository.getAllSortedAccounts(0, 1).size() > 0);
    }

    @Test
    public void testGetNewAccountId() {
        Assert.assertEquals(1L, accountRepository.getNewAccountId().longValue());
    }

    @Test
    public void testGetAccountById() {
        accountRepository.openAccountIfNotExists(getMockAccountObject());
        Assert.assertTrue(null != accountRepository.getAccountByAccountId(1));
    }

    @Test
    public void testGetSortedAccounts() {
        for (int i = 0; i < 5; i++) {
            accountRepository.openAccountIfNotExists(getMockAccountObject());
        }
        List<Account> accountList = accountRepository.getAllSortedAccounts(4, 2);
        Assert.assertEquals(1, accountList.size());
    }

    private Account getMockAccountObject() {
        Account account = new Account();
        account.setName("Mango");
        return account;
    }
}