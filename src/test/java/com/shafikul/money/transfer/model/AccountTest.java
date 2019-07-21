package com.shafikul.money.transfer.model;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

	@InjectMocks
	private Account account = new Account();

	@Test
	public void accountTest() {
		assertEquals(BigDecimal.ZERO, account.getBalance());
	}
}
