package com.shafikul.money.transfer.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.shafikul.money.transfer.utility.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

	private Integer id;

	private String name;

	private BigDecimal balance = BigDecimal.ZERO;

	private final Map<Long, Transaction> transactions = new ConcurrentHashMap<Long, Transaction>();

	private final transient Lock lock = new ReentrantLock();

	public BigDecimal getBalance() {
		try {
			lock.lock();
			return balance;
		} finally {
			lock.unlock();
		}
	}

	public Boolean withDrawFromAccount(BigDecimal amount) {
		try {
			if (lock.tryLock(Constants.LOCK_WAIT_TIME, TimeUnit.MICROSECONDS)) {
				try {
					if (balance.compareTo(amount) > 0) {
						balance = balance.subtract(amount);
						return true;
					}
					return false;
				} finally {
					lock.unlock();
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean sendToAccount(BigDecimal amount) {
		try {
			if (lock.tryLock(Constants.LOCK_WAIT_TIME, TimeUnit.MICROSECONDS)) {
				try {
					balance = balance.add(amount);
					return true;
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}