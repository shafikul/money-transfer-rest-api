package com.shafikul.money.transfer.core.beans;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountBean {

	@NonNull
	private String name;

	@NonNull
	private BigDecimal primaryBalance;

	@NonNull
	private Date birth;
}
