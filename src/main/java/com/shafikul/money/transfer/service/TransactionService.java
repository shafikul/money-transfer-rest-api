package com.shafikul.money.transfer.service;

import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.beans.TransactionBean;
import com.shafikul.money.transfer.core.dto.TransactionDto;
import com.shafikul.money.transfer.core.enums.ResponseType;
import com.shafikul.money.transfer.core.enums.State;
import com.shafikul.money.transfer.core.enums.TransactionType;
import com.shafikul.money.transfer.model.Account;
import com.shafikul.money.transfer.model.Transaction;
import com.shafikul.money.transfer.repository.AccountRepository;
import com.shafikul.money.transfer.repository.TransactionRepository;
import com.shafikul.money.transfer.transformer.TransactionTransformer;
import com.shafikul.money.transfer.utility.Messages;
import com.shafikul.money.transfer.utility.Status;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private static TransactionService instance = null;

    private TransactionTransformer transactionTransformer = TransactionTransformer.getInstance();

    private TransactionService() {

    }

    public static TransactionService getInstance() {
        if (null == instance) {
            instance = new TransactionService();
        }
        return instance;
    }

    public BaseResponse getAllTransactions(Integer start, Integer limit) {
        List<Transaction> list = TransactionRepository.INSTANCE.getAllSortedTransactions(start, limit);
        List<TransactionDto> transactionList = list.stream().map(source -> transactionTransformer.transform(source))
                .collect(Collectors.toList());
        return BaseResponse.builder().type(ResponseType.SUCCESS).status(200).result(transactionList)
                .message(ResponseType.SUCCESS.name()).build();
    }

    public Transaction getTransactionDetailsById(Long transactionId) {
        return TransactionRepository.INSTANCE.getTransactionById(transactionId);
    }

    public BaseResponse transferMoneyFromAccountToAccount(TransactionBean bean) {
        Account fromAccount = AccountRepository.INSTANCE.getAccountByAccountId(bean.getFromAccount());
        Account toAccount = AccountRepository.INSTANCE.getAccountByAccountId(bean.getToAccount());
        if (ValidatorUtil.genericNullCheck(fromAccount) || ValidatorUtil.genericNullCheck(toAccount)) {
            return BaseResponse.builder().type(ResponseType.ERROR).status(Status.NOT_FOUND).message(Messages.NOT_FOUND)
                    .build();
        }
        Transaction transaction = getTransactionObject(fromAccount, toAccount, bean.getMoney(),
                TransactionType.TRANSFER);
        /**
         * Actually the server which I have used is a Thread Based server. If It were a
         * Non Blocking server then this //new Thread(transaction).start() code should
         * be used
         */
        transaction.run();
        saveTransactionHistory(transaction);
        saveAccountTransactionHistory(fromAccount, transaction);
        saveAccountTransactionHistory(toAccount, transaction);
        return transactionResponseBasedOnState(transaction);
    }

    public BaseResponse withDrawMoneyFromAccount(TransactionBean bean) {
        Account account = AccountRepository.INSTANCE.getAccountByAccountId(bean.getFromAccount());
        if (ValidatorUtil.genericNullCheck(account)) {
            return BaseResponse.builder().type(ResponseType.ERROR).status(Status.NOT_FOUND).message(Messages.NOT_FOUND)
                    .build();
        }
        return withDrawMoneyFromAccount(account, bean.getMoney());
    }

    public BaseResponse withDrawMoneyFromAccount(Account account, BigDecimal amount) {
        Transaction transaction = getTransactionObject(account, account, amount, TransactionType.WITH_DRAW);
        transaction.run();
        saveTransactionHistory(transaction);
        saveAccountTransactionHistory(account, transaction);
        return transactionResponseBasedOnState(transaction);
    }

    public BaseResponse depositMoneyToAccount(TransactionBean bean) {
        Account account = AccountRepository.INSTANCE.getAccountByAccountId(bean.getToAccount());
        if (ValidatorUtil.genericNullCheck(account)) {
            return BaseResponse.builder().type(ResponseType.ERROR).status(Status.NOT_FOUND).message(Messages.NOT_FOUND)
                    .build();
        }
        return depositMoneyToAccount(account, bean.getMoney());
    }

    public BaseResponse depositMoneyToAccount(Account account, BigDecimal amount) {
        Transaction transaction = getTransactionObject(account, account, amount, TransactionType.DEPOSIT);
        transaction.run();
        saveTransactionHistory(transaction);
        saveAccountTransactionHistory(account, transaction);
        return transactionResponseBasedOnState(transaction);
    }

    private Transaction getTransactionObject(Account fromAccount, Account toAccount, BigDecimal amount,
                                             TransactionType type) {
        return Transaction.builder().amount(amount).fromAccount(fromAccount).toAccount(toAccount).state(State.NEW)
                .type(type).timestamp(System.currentTimeMillis())
                .transactionId(TransactionRepository.INSTANCE.getNewTransactionId()).build();
    }

    private void saveTransactionHistory(Transaction transaction) {
        TransactionRepository.INSTANCE.getAllTransactions().put(transaction.getTransactionId(), transaction);
    }

    private void saveAccountTransactionHistory(Account account, Transaction transaction) {
        account.getTransactions().put(transaction.getTransactionId(), transaction);
    }

    private BaseResponse transactionResponseBasedOnState(Transaction transaction) {
        switch (transaction.getState()) {
            case COMPLETE:
                return BaseResponse.builder().type(ResponseType.SUCCESS).status(Status.CREATED)
                        .result(transactionTransformer.transform(transaction)).message(transaction.getState().name())
                        .build();
            case NEW:
            case INCOMPLETE:
                return BaseResponse.builder().type(ResponseType.ERROR).status(Status.UNPROCESSED_ENTITY)
                        .result(transactionTransformer.transform(transaction)).message(Messages.UNPROCESSED_ENTITY).build();
            case LOCK_ERROR:
                return BaseResponse.builder().type(ResponseType.ERROR).status(Status.LOCKED)
                        .result(transactionTransformer.transform(transaction)).message(transaction.getState().name())
                        .build();
            case INSUFFICIENT_BALANCE:
                return BaseResponse.builder().type(ResponseType.ERROR).status(Status.FORBIDDEN)
                        .result(transactionTransformer.transform(transaction)).message(transaction.getState().name())
                        .build();
            case INVALID_PAYLOAD:
                return BaseResponse.builder().type(ResponseType.ERROR).status(Status.BAD_REQUEST)
                        .result(transactionTransformer.transform(transaction)).message(transaction.getState().name())
                        .build();
            case FAILURE:
            default:
                return BaseResponse.builder().type(ResponseType.ERROR).status(Status.PAYMENT_FAILUE)
                        .result(transactionTransformer.transform(transaction)).message(Messages.TRANSACTION_FAILED).build();
        }
    }
}
