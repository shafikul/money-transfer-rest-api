package com.shafikul.money.transfer.transformer;

import com.shafikul.money.transfer.core.dto.TransactionDto;
import com.shafikul.money.transfer.core.enums.State;
import com.shafikul.money.transfer.core.enums.TransactionType;
import com.shafikul.money.transfer.model.Account;
import com.shafikul.money.transfer.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransactionTransformerTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private TransactionTransformer transactionTransformer;

    @Mock
    private Transaction transaction;

    @Test
    public void teatTransformException() {
        transaction = Transaction.builder().transactionId(1L)
                .amount(new BigDecimal(10.0)).type(TransactionType.TRANSFER).state(State.COMPLETE).build();
        TransactionDto dto = transactionTransformer.getInstance().transform(transaction);
        Assert.assertTrue(dto.getAmount().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testTransform() {
        Account fromAccount = new Account();
        fromAccount.setId(1);
        Account toAccount = new Account();
        toAccount.setId(2);
        transaction = Transaction.builder().transactionId(1L)
                .amount(new BigDecimal(10.0))
                .type(TransactionType.TRANSFER)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .state(State.COMPLETE)
                .build();
        TransactionDto dto = transactionTransformer.getInstance().transform(transaction);
        Assert.assertTrue(dto.getAmount().compareTo(BigDecimal.ZERO) > 0);
        Assert.assertTrue(dto.getFromAccountId().equals(1));
        Assert.assertTrue(dto.getToAccountId().equals(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTransformBack() {
        transactionTransformer.transformBack(new TransactionDto());
    }
}
