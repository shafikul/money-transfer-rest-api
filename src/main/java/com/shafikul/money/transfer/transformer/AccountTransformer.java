package com.shafikul.money.transfer.transformer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.shafikul.money.transfer.core.dto.AccountDto;
import com.shafikul.money.transfer.core.dto.TransactionDto;
import com.shafikul.money.transfer.model.Account;
import com.shafikul.money.transfer.model.Transaction;
import com.shafikul.money.transfer.utility.Transformer;

public class AccountTransformer implements Transformer<Account, AccountDto> {
	private static AccountTransformer instance = null;

	private AccountTransformer() {
		// TODO Auto-generated constructor stub
	}

	public static AccountTransformer getInstance() {
		if (null == instance) {
			instance = new AccountTransformer();
		}
		return instance;
	}

	@Override
	public AccountDto transform(Account source) {
		AccountDto accountDto = new AccountDto();
		accountDto.setBalance(source.getBalance());
		accountDto.setId(source.getId());
		accountDto.setName(source.getName());
		return accountDto;
	}

	public AccountDto transform(Account source, Integer recentTransactionsLimit) {
		AccountDto accountDto = transform(source);
		accountDto.setTransactions(getAccountRelatedTransactions(source, recentTransactionsLimit));
		return accountDto;
	}

	@Override
	public Account transformBack(AccountDto target) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<TransactionDto> getAccountRelatedTransactions(Account account, Integer limit) {
		return account.getTransactions().values().stream()
				.sorted(Comparator.comparingLong(Transaction::getTimestamp).reversed()).limit(limit)
				.map(source -> TransactionTransformer.getInstance().transform(source)).collect(Collectors.toList());

	}
}
