package com.shafikul.money.transfer.utility;

public class Constants {
	public static Integer TRANSACTION_LIMIT_FOR_ACCOUNT = 100;
	public static String contentType = "Content-Type";
	public static String contentValue = "application/json";
	public static Integer DEFAULT_START_POSITION = 0;	
	public static Integer DEFAULT_LIMIT = 10;
	public static Integer INVALID_ID = -1;
	public static Integer LOCK_WAIT_TIME = 50;
	
	//all end point URL
	public static String ALL_ACCOUNT_PATH = "/api/v1/account/all";
	public static String ACCOUNT_DETAILS_PATH = "/api/v1/account";
	public static String OPENT_ACCOUNT_PATH = "/api/v1/account/create";
	public static String TRANSFER_MONEY_PATH = "/api/v1/account/transfer";
	public static String WITH_DRWA_MONEY_PATH = "/api/v1/account/with-draw";
	public static String DEPOSIT_MONEY_PATH = "/api/v1/account/send";
	public static String ALL_TRANSATIONS_PATH = "/api/v1/transactions/all";
	
	public static String REQUEST_TYPE_POST = "POST";
	
}
