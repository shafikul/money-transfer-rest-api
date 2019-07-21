package com.shafikul.money.transfer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.shafikul.money.transfer.core.beans.AccountBean;
import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.dto.AccountDto;
import com.shafikul.money.transfer.core.enums.ResponseType;
import com.shafikul.money.transfer.model.Account;
import com.shafikul.money.transfer.repository.AccountRepository;
import com.shafikul.money.transfer.transformer.AccountTransformer;
import com.shafikul.money.transfer.utility.Messages;
import com.shafikul.money.transfer.utility.Status;

public class AccountService {

	private static AccountService instance;
	private AccountTransformer accountTransformer = AccountTransformer.getInstance();

	public static AccountService getInstance() {
		if (null == instance) {
			instance = new AccountService();
		}
		return instance;
	}

	private AccountService() {
	}

	public BaseResponse openAccount(AccountBean accountBean) {
		Account account = new Account();
		account.setName(accountBean.getName());
		AccountRepository.INSTANCE.openAccountIfNotExists(account);
		if (accountBean.getPrimaryBalance().compareTo(BigDecimal.ZERO) > 0) {
			TransactionService.getInstance().depositMoneyToAccount(account, accountBean.getPrimaryBalance());
		}
		return BaseResponse.builder().type(ResponseType.SUCCESS).status(Status.CREATED)
				.result(accountTransformer.transform(account)).message(Messages.CREATED).build();
	}

	public BaseResponse getAllAccounts(Integer start, Integer limit) {
		List<Account> list = AccountRepository.INSTANCE.getAllSortedAccounts(start, limit);
		List<AccountDto> account = list.stream().map(source -> accountTransformer.transform(source))
				.collect(Collectors.toList());
		return BaseResponse.builder().type(ResponseType.SUCCESS).result(account).status(Status.OK)
				.message(ResponseType.SUCCESS.name()).build();
	}

	public BaseResponse getAccountDetailsById(Integer accountId, Integer transactionLimit) {
		Account account = AccountRepository.INSTANCE.getAccountByAccountId(accountId);
		if (null == account) {
			return BaseResponse.builder().type(ResponseType.ERROR).status(Status.NOT_FOUND).message(Messages.NOT_FOUND)
					.build();
		}
		AccountDto details = accountTransformer.transform(account, transactionLimit);
		return BaseResponse.builder().type(ResponseType.SUCCESS).status(Status.OK).result(details).message(Messages.OK)
				.build();
	}
}
