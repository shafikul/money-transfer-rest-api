package com.shafikul.money.transfer.service;

import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.beans.TransactionBean;
import com.shafikul.money.transfer.core.enums.ResponseType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransactionServiceTest {

    private TransactionService transactionService = TransactionService.getInstance();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTransactions() {
        BaseResponse baseResponse = transactionService.getAllTransactions(0, 5);
        Assert.assertEquals(ResponseType.SUCCESS, baseResponse.getType());
    }

    @Test
    public void testTransferMoneyFromAccountToAccount() {
        BaseResponse baseResponse = transactionService.transferMoneyFromAccountToAccount(getMockTransactionObject());
        Assert.assertEquals(ResponseType.ERROR, baseResponse.getType());
    }

    @Test
    public void testWithDrawMoneyFromAccount() {
        BaseResponse baseResponse = transactionService.withDrawMoneyFromAccount(getMockTransactionObject());
        Assert.assertEquals(ResponseType.ERROR, baseResponse.getType());
    }

    @Test
    public void testDepositMoneyToAccount() {
        BaseResponse baseResponse = transactionService.depositMoneyToAccount(getMockTransactionObject());
        Assert.assertEquals(ResponseType.ERROR, baseResponse.getType());
    }

    private TransactionBean getMockTransactionObject() {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setToAccount(1);
        transactionBean.setFromAccount(1);
        transactionBean.setMoney(new BigDecimal(10.0));
        return transactionBean;
    }
}
