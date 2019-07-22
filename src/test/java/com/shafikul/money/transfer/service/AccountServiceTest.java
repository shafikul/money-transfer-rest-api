package com.shafikul.money.transfer.service;

import com.shafikul.money.transfer.core.beans.AccountBean;
import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.enums.ResponseType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountServiceTest {


    private AccountService accountService = AccountService.getInstance();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOpenUserAccount() {
        BaseResponse baseResponse = accountService.openAccount(getMockAccountBeanObject());
        Assert.assertEquals(ResponseType.SUCCESS, baseResponse.getType());

    }

    @Test
    public void testGetAllAccounts() {
        for (int i = 1; i <= 5; i++) {
            accountService.openAccount(getMockAccountBeanObject());
        }
        BaseResponse baseResponse = accountService.getAllAccounts(4, 2);
        Assert.assertEquals(ResponseType.SUCCESS, baseResponse.getType());
    }

    @Test
    public void testGetAccountDetailsById() {
        BaseResponse baseResponse = accountService.openAccount(getMockAccountBeanObject());
        Assert.assertEquals(ResponseType.SUCCESS, baseResponse.getType());
        Assert.assertNotEquals(null, accountService.getAccountDetailsById(1, 1));
    }

    private AccountBean getMockAccountBeanObject() {
        AccountBean accountBean = new AccountBean();
        accountBean.setPrimaryBalance(new BigDecimal(0.0));
        accountBean.setBirth(getMockDate());
        accountBean.setName("mango");
        return accountBean;
    }

    private Date getMockDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2009-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
