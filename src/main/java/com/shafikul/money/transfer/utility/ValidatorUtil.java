package com.shafikul.money.transfer.utility;

import java.io.IOException;
import java.math.BigDecimal;

import com.shafikul.money.transfer.core.beans.AccountBean;
import com.shafikul.money.transfer.core.beans.TransactionBean;
import com.shafikul.money.transfer.core.enums.TransactionType;

import net.freeutils.httpserver.HTTPServer;

public class ValidatorUtil {

    public static boolean validate(TransactionBean transaction, TransactionType type) {
        if (validate(transaction) && validate(transaction.getMoney()) && validate(transaction.getFromAccount())
                && validate(transaction.getToAccount())) {
            switch (type) {
                case TRANSFER:
                    return !validateSameAccountId(transaction.getFromAccount(), transaction.getToAccount());
                case WITH_DRAW:
                case DEPOSIT:
                    return validateSameAccountId(transaction.getFromAccount(), transaction.getToAccount());
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    public static Boolean validate(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static Boolean validate(Integer accountId) {
        return accountId.intValue() > 0;
    }

    public static boolean validate(TransactionBean transaction) {
        if (genericNullCheck(transaction.getToAccount()) || genericNullCheck(transaction.getFromAccount())
                || genericNullCheck(transaction.getMoney()))
            return false;
        return true;
    }

    public static Boolean validateSameAccountId(Integer fromAccount, Integer toAccount) {
        return fromAccount.equals(toAccount);
    }

    public static Boolean validatePrimaryBalanceNonNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean validate(AccountBean account) {
        if ((genericNullCheck(account.getBirth()) || genericNullCheck(account.getName())
                || genericNullCheck(account.getPrimaryBalance())
                || validatePrimaryBalanceNonNegative(account.getPrimaryBalance()))) {
            return false;
        }
        return true;
    }

    public static <T> Boolean genericNullCheck(T item) {
        return null == item;
    }

    public static Integer checkOrGetDefaultStartParams(HTTPServer.Request request) {
        Integer start;
        try {
            start = Integer.parseInt(request.getParams().getOrDefault("start", "-1"));
            if (start >= 0) {
                return start;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Constants.DEFAULT_START_POSITION;
    }

    public static Integer checkOrGetDefaultLimitParams(HTTPServer.Request request) {
        Integer limit;
        try {
            limit = Integer.parseInt(request.getParams().getOrDefault("limit", "0"));
            if (limit > 0) {
                return limit;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Constants.DEFAULT_LIMIT;
    }

    public static Integer validateAndGetIDParams(HTTPServer.Request request) {
        int id = Constants.INVALID_ID;
        try {
            id = Integer.parseInt(request.getParams().getOrDefault("id", "-1"));
            if (id > 0) {
                return id;
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
