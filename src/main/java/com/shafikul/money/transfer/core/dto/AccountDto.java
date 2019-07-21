package com.shafikul.money.transfer.core.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class AccountDto {
	private Integer id;

	private String name;

	private BigDecimal balance = BigDecimal.ZERO;

	private List<TransactionDto> transactions;

}
