package com.shafikul.money.transfer.core.dto;

import java.math.BigDecimal;

import com.shafikul.money.transfer.core.enums.State;
import com.shafikul.money.transfer.core.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionDto {
	private Long transactionId;
	private Integer fromAccountId;
	private Integer toAccountId;
	private BigDecimal amount;
	private Long timestamp;
	private State state;
	private TransactionType type;
}
