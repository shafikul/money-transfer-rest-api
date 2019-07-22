package com.shafikul.money.transfer.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.shafikul.money.transfer.model.Transaction;

public enum TransactionRepository {

    INSTANCE;

    private AtomicLong autoIncrementId = new AtomicLong(0L);

    private Map<Long, Transaction> transactions = new ConcurrentHashMap<Long, Transaction>();

    public Transaction getTransactionById(Long transactionId) {
        return this.transactions.get(transactionId);
    }

    public Map<Long, Transaction> getAllTransactions() {
        return this.transactions;
    }

    public List<Transaction> getAllSortedTransactions(Integer start, Integer limit) {
        return transactions.values().stream().sorted(Comparator.comparingLong(Transaction::getTransactionId))
                .skip(start).limit(limit).collect(Collectors.toList());
    }

    public Long getNewTransactionId() {
        return autoIncrementId.incrementAndGet();
    }
}
