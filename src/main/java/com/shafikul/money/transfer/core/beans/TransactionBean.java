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
	@NonNull
	private Integer toAccount;
	@NonNull
	private Integer fromAccount;
	@NonNull
	private BigDecimal money;
}
