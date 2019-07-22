package com.shafikul.money.transfer.core.beans;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionBean {

    private Integer toAccount;

    private Integer fromAccount;

    private BigDecimal money;
}
