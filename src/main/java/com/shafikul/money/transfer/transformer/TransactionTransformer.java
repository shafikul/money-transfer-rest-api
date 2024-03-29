package com.shafikul.money.transfer.transformer;

import com.shafikul.money.transfer.core.dto.TransactionDto;
import com.shafikul.money.transfer.model.Transaction;
import com.shafikul.money.transfer.utility.Transformer;

public class TransactionTransformer implements Transformer<Transaction, TransactionDto> {
    private static TransactionTransformer instance = null;

    private TransactionTransformer() {
    }

    public static TransactionTransformer getInstance() {
        if (null == instance) {
            instance = new TransactionTransformer();
        }
        return instance;
    }

    @Override
    public TransactionDto transform(Transaction source) {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(source.getAmount());
        if (null != source.getFromAccount()) {
            dto.setFromAccountId(source.getFromAccount().getId());
        }
        if (null != source.getToAccount()) {
            dto.setToAccountId(source.getToAccount().getId());
        }
        dto.setTimestamp(source.getTimestamp());
        dto.setState(source.getState());
        dto.setType(source.getType());
        return dto;
    }

    @Override
    public Transaction transformBack(TransactionDto target) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
