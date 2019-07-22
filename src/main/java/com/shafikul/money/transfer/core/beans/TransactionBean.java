package com.shafikul.money.transfer.core.beans;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionBean {

    private Integer toAccount;

    private Integer fromAccount;

    private BigDecimal money;
}
