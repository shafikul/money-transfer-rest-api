package com.shafikul.money.transfer.utility;

import com.shafikul.money.transfer.core.beans.AccountBean;
import com.shafikul.money.transfer.core.beans.TransactionBean;
import com.shafikul.money.transfer.core.enums.TransactionType;
import net.freeutils.httpserver.HTTPServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidatorUtilTest {

    @InjectMocks
    private ValidatorUtil validatorUtil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    HTTPServer.Request request;

    @Mock
    HTTPServer.Response response;

    @Test
    public void testAmountNonNegative() {
        Assert.assertEquals(true, validatorUtil.validate(new BigDecimal(10.0)));
        Assert.assertFalse(validatorUtil.validate(new BigDecimal(-10.0)));
        Assert.assertFalse(validatorUtil.validate(new BigDecimal(0.0)));
    }

    @Test
    public void testValidAccountId() {
        Assert.assertEquals(true, validatorUtil.validate(1));
        Assert.assertFalse(validatorUtil.validate(new BigDecimal(0)));
    }

    @Test
    public void testGenericNullCheck() {
        Assert.assertTrue(validatorUtil.genericNullCheck(null));
        Assert.assertFalse(validatorUtil.genericNullCheck(0));
    }

    @Test
    public void testValidateAccountPayload() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2009-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AccountBean accountBean = new AccountBean();
        accountBean.setPrimaryBalance(new BigDecimal(0.0));
        accountBean.setBirth(date);
        accountBean.setName("mango");
        Assert.assertTrue(validatorUtil.validate(accountBean));
        accountBean.setBirth(null);
        Assert.assertFalse(validatorUtil.validate(accountBean));
    }

    @Test
    public void testValidTransactionPayload() {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setFromAccount(1);
        transactionBean.setToAccount(2);
        transactionBean.setMoney(new BigDecimal(1.0));
        Assert.assertTrue(validatorUtil.validate(transactionBean));
        transactionBean.setMoney(null);
        Assert.assertFalse(validatorUtil.validate(transactionBean));
    }

    @Test
    public void validateSameAccountId() {
        Integer toAccountId = 1;
        Integer fromAccountId = 1;
        Assert.assertTrue(validatorUtil.validateSameAccountId(toAccountId, fromAccountId));
        toAccountId = 100;
        Assert.assertFalse(validatorUtil.validateSameAccountId(toAccountId, fromAccountId));
    }

    @Test
    public void testValidatePrimaryBalanceNonNegative() {
        Assert.assertTrue(validatorUtil.validatePrimaryBalanceNonNegative(new BigDecimal(-1)));
        Assert.assertFalse(validatorUtil.validatePrimaryBalanceNonNegative(new BigDecimal(0)));
        Assert.assertFalse(validatorUtil.validatePrimaryBalanceNonNegative(new BigDecimal(1)));
    }

    @Test
    public void testTransferPayload() {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setMoney(new BigDecimal(10.0));
        transactionBean.setFromAccount(1);
        transactionBean.setToAccount(2);
        Assert.assertTrue(validatorUtil.validate(transactionBean, TransactionType.TRANSFER));
        transactionBean.setFromAccount(2);
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.TRANSFER));
    }

    @Test
    public void testDepositPayload() {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setMoney(new BigDecimal(10.0));
        transactionBean.setFromAccount(1);
        transactionBean.setToAccount(1);
        Assert.assertTrue(validatorUtil.validate(transactionBean, TransactionType.DEPOSIT));
        transactionBean.setToAccount(2);
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.DEPOSIT));
        transactionBean.setToAccount(null);
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.DEPOSIT));
    }

    @Test
    public void testWithDrawPayload() {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setMoney(new BigDecimal(10.0));
        transactionBean.setFromAccount(1);
        transactionBean.setToAccount(1);
        Assert.assertTrue(validatorUtil.validate(transactionBean, TransactionType.WITH_DRAW));
        transactionBean.setToAccount(2);
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.WITH_DRAW));
        transactionBean.setToAccount(null);
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.WITH_DRAW));
        transactionBean.setToAccount(1);
        transactionBean.setMoney(new BigDecimal(0));
        Assert.assertFalse(validatorUtil.validate(transactionBean, TransactionType.WITH_DRAW));
    }

    @Test
    public void testCheckOrGetDefaultStartParams() {
        Map<String, String> req = new HashMap<>();
        req.put("start", "5");

        try {
            Mockito.when(request.getParams()).thenReturn(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start = validatorUtil.checkOrGetDefaultStartParams(request);

        Assert.assertEquals(5L, start);
        try {
            Mockito.when(request.getParams()).thenReturn(new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        start = validatorUtil.checkOrGetDefaultStartParams(request);

        Assert.assertEquals(0L, start);
    }

    @Test
    public void  testCheckOrGetDefaultLimitParams(){
        Map<String, String> req = new HashMap<>();
        req.put("limit", "5");

        try {
            Mockito.when(request.getParams()).thenReturn(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long limit = validatorUtil.checkOrGetDefaultLimitParams(request);

        Assert.assertEquals(5L, limit);
        try {
            Mockito.when(request.getParams()).thenReturn(new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        limit = validatorUtil.checkOrGetDefaultLimitParams(request);

        Assert.assertEquals(10L, limit);
    }

    @Test
    public void  testValidateAndGetIDParams(){
        Map<String, String> req = new HashMap<>();
        req.put("id", "5");

        try {
            Mockito.when(request.getParams()).thenReturn(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long id = validatorUtil.validateAndGetIDParams(request);

        Assert.assertEquals(5L, id);
        try {
            Mockito.when(request.getParams()).thenReturn(new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        id = validatorUtil.validateAndGetIDParams(request);

        Assert.assertEquals(-1L, id);
    }
}
