package com.shafikul.money.transfer.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.shafikul.money.transfer.model.Account;

public enum AccountRepository {

	INSTANCE;

	private AtomicInteger autoIncrementId = new AtomicInteger(0);

	private Map<Integer, Account> accounts = new ConcurrentHashMap<>();

	public void openAccountIfNotExists(Account account) {
		if (null == account.getId() || account.getId().equals(0)) {
			account.setId(getNewAccoutId());
		}
		updateAccount(account);
	}

	public Account getAccountByAccountId(Integer accountId) {
		return this.accounts.get(accountId);
	}

	public Map<Integer, Account> getAllAccounts() {
		return this.accounts;
	}

	public List<Account> getAllSortedAccounts(Integer start, Integer limit) {
		return accounts.values().stream().sorted(Comparator.comparingInt(Account::getId)).skip(start).limit(limit)
				.collect(Collectors.toList());
	}

	public Boolean checkIfAccountExists(Integer accountId) {
		return accounts.containsKey(accountId);
	}

	public void updateAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	public Integer getNewAccoutId() {
		return autoIncrementId.incrementAndGet();
	}
}
