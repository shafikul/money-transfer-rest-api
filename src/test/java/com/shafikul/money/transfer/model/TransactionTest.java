package com.shafikul.money.transfer.model;

import com.shafikul.money.transfer.core.enums.State;
import com.shafikul.money.transfer.core.enums.TransactionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransactionTest {

    @InjectMocks
    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRun() {
        Transaction transferObject = getMockTransactionObjectForTransfer();
        transferObject.run();
        Assert.assertEquals(State.INSUFFICIENT_BALANCE, transferObject.getState());
        Transaction transaction = getMockTransactionObjectForDeposit();
        transaction.run();
        Assert.assertEquals(State.COMPLETE, transaction.getState());
        transaction.setType(TransactionType.WITH_DRAW);
        transaction.setAmount(new BigDecimal(2.0));
        transaction.run();
        Assert.assertEquals(State.COMPLETE, transaction.getState());
    }

    @Test
    public void testTransfer() {
        Transaction transferObject = getMockTransactionObjectForTransfer();
        transferObject.run();
        Assert.assertEquals(State.INSUFFICIENT_BALANCE, transferObject.getState());
    }

    @Test
    public void testWithDraw() {
        Transaction tnx = getMockTransactionObjectForWithDraw();
        tnx.run();
        Assert.assertEquals(State.COMPLETE, tnx.getState());
        tnx.setAmount(new BigDecimal(95.0));
        tnx.run();
        Assert.assertEquals(State.INSUFFICIENT_BALANCE, tnx.getState());
        tnx.setAmount(new BigDecimal(94.99));
        tnx.run();
        Assert.assertEquals(State.COMPLETE, tnx.getState());
    }

    @Test
    public void testDeposit() {
        Transaction depositObject = getMockTransactionObjectForDeposit();
        depositObject.run();
        Assert.assertEquals(State.COMPLETE, depositObject.getState());
    }


    private Transaction getMockTransactionObjectForTransfer() {
        Account toAccount = new Account();
        toAccount.setId(1);
        Account fromAccount = new Account();
        fromAccount.setId(2);
        return Transaction.builder().transactionId(1L).toAccount(toAccount)
                .fromAccount(fromAccount)
                .amount(new BigDecimal(10.5))
                .timestamp(System.currentTimeMillis()).type(TransactionType.TRANSFER).build();
    }

    private Transaction getMockTransactionObjectForDeposit() {
        Account toAccount = new Account();
        toAccount.setId(1);

        return Transaction.builder().transactionId(1L).toAccount(toAccount)
                .fromAccount(toAccount)
                .amount(new BigDecimal(5.0))
                .timestamp(System.currentTimeMillis()).type(TransactionType.DEPOSIT).build();
    }

    private Transaction getMockTransactionObjectForWithDraw() {
        Account toAccount = new Account();
        toAccount.setId(1);
        toAccount.sendToAccount(new BigDecimal(100.0));

        return Transaction.builder().transactionId(1L).toAccount(toAccount)
                .fromAccount(toAccount)
                .amount(new BigDecimal(5.0))
                .timestamp(System.currentTimeMillis()).type(TransactionType.WITH_DRAW).build();
    }
}
