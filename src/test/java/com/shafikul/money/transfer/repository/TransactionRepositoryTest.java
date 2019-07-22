package com.shafikul.money.transfer.repository;

import com.shafikul.money.transfer.core.enums.TransactionType;
import com.shafikul.money.transfer.model.Account;
import com.shafikul.money.transfer.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository = TransactionRepository.INSTANCE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNewTransactionId() {
        Assert.assertEquals(1, transactionRepository.getNewTransactionId().intValue());
    }

    @Test
    public void testTransactionById() {
        Transaction transaction = getMockTransactionObject();
        transactionRepository.getAllTransactions().put(transaction.getTransactionId(), transaction);
        Assert.assertTrue(null != transactionRepository.getTransactionById(1L));
    }

    @Test
    public void testGetAllTransactions() {
        for (long i = 1; i <= 5; i++) {
            Transaction transaction = getMockTransactionObject();
            transaction.setTransactionId(i);
            transactionRepository.getAllTransactions().put(transaction.getTransactionId(), transaction);
        }
        Assert.assertTrue(transactionRepository.getAllTransactions().size() == 5);
    }

    @Test
    public void testGetAllSortedTransactions() {
        for (long i = 1; i <= 5; i++) {
            Transaction transaction = getMockTransactionObject();
            transaction.setTransactionId(i);
            transactionRepository.getAllTransactions().put(transaction.getTransactionId(), transaction);
        }
        List<Transaction> transactions = transactionRepository.getAllSortedTransactions(4, 2);
        Assert.assertEquals(1, transactions.size());
    }

    private Transaction getMockTransactionObject() {
        Account toAccount = new Account();
        toAccount.setId(1);
        Account fromAccount = new Account();
        fromAccount.setId(2);
        return Transaction.builder().transactionId(1L).toAccount(toAccount)
                .fromAccount(fromAccount)
                .amount(new BigDecimal(10.5))
                .timestamp(System.currentTimeMillis()).type(TransactionType.TRANSFER).build();
    }
}
