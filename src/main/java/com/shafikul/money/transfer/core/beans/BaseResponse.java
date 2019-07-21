package com.shafikul.money.transfer.core.beans;

import com.shafikul.money.transfer.core.enums.ResponseType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class BaseResponse {

	private ResponseType type;

	private int status;

	private String message;

	private Object result;

	private Object error;

}
