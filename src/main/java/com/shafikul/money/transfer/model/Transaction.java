package com.shafikul.money.transfer.model;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.shafikul.money.transfer.core.enums.State;
import com.shafikul.money.transfer.core.enums.TransactionType;
import com.shafikul.money.transfer.utility.Constants;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

	private Long transactionId;

	private Account fromAccount;

	private Account toAccount;

	private BigDecimal amount;

	private Long timestamp;

	private State state;

	private TransactionType type;

	public void run() {
		switch (type) {
		case TRANSFER:
			transfer();
			break;
		case WITH_DRAW:
			withDraw();
			break;
		case DEPOSIT:
			deposit();
			break;
		default:
			break;
		}
	}

	public synchronized void transfer() {
		if (State.COMPLETE == withDraw()) {
			if (State.COMPLETE != deposit()) {
				// refund
				fromAccount.sendToAccount(amount);
			}
		}
	}

	private State deposit() {
		try {
			final Lock transferLock = toAccount.getLock();
			if (transferLock.tryLock(Constants.LOCK_WAIT_TIME, TimeUnit.MILLISECONDS)) {
				try {
					if (toAccount.sendToAccount(amount)) {
						return state = State.COMPLETE;
					} else {
						return state = State.FAILURE;
					}
				} finally {
					transferLock.unlock();
				}
			} else {
				return state = State.LOCK_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return state = State.FAILURE;
		}
	}

	private State withDraw() {
		try {
			final Lock withDrawlock = fromAccount.getLock();
			if (withDrawlock.tryLock(Constants.LOCK_WAIT_TIME, TimeUnit.MILLISECONDS)) {
				try {
					// check balance
					if (fromAccount.getBalance().compareTo(amount) > 0) {
						if (fromAccount.withDrawFromAccount(amount)) {
							return state = State.COMPLETE;
						} else {
							return state = State.FAILURE;
						}
					} else {
						return state = State.INSUFFICIENT_BALANCE;
					}
				} finally {
					withDrawlock.unlock();
				}
			} else {
				return state = State.LOCK_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return state = State.FAILURE;
		}
	}
}
