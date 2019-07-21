package com.shafikul.money.transfer.context;

import java.io.IOException;

import com.google.gson.Gson;
import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.beans.TransactionBean;
import com.shafikul.money.transfer.core.enums.ResponseType;
import com.shafikul.money.transfer.core.enums.TransactionType;
import com.shafikul.money.transfer.service.TransactionService;
import com.shafikul.money.transfer.utility.DataConversionUtil;
import com.shafikul.money.transfer.utility.Messages;
import com.shafikul.money.transfer.utility.Status;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import net.freeutils.httpserver.HTTPServer;

public class MoneyTransferFromAccountToAccount implements HTTPServer.ContextHandler {
	public int serve(HTTPServer.Request req, HTTPServer.Response resp) throws IOException {
		DataConversionUtil.setHeader(resp);
		BaseResponse baseResponse = null;

		TransactionBean payload = new Gson().fromJson(DataConversionUtil.streamToString(req.getBody()),
				TransactionBean.class);

		if (!ValidatorUtil.validate(payload, TransactionType.TRANSFER)) {
			baseResponse = BaseResponse.builder().type(ResponseType.ERROR).status(Status.UNPROCESSED_ENTITY)
					.message(Messages.INVALID_PAYLOAD).build();
			resp.send(baseResponse.getStatus(), DataConversionUtil.make().toJson(baseResponse));
			return 0;
		}
		baseResponse = TransactionService.getInstance().transferMoneyFromAccountToAccount(payload);
		resp.send(baseResponse.getStatus(), DataConversionUtil.make().toJson(baseResponse));
		return 0;
	}
}
