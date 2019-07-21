package com.shafikul.money.transfer.context;

import java.io.IOException;

import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.service.TransactionService;
import com.shafikul.money.transfer.utility.DataConversionUtil;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import net.freeutils.httpserver.HTTPServer;

public class GetAllTransactions implements HTTPServer.ContextHandler {
	public int serve(HTTPServer.Request req, HTTPServer.Response resp) throws IOException {
		DataConversionUtil.setHeader(resp);
		int start = ValidatorUtil.checkOrGetDefaultStartParams(req);
		int limit = ValidatorUtil.checkOrGetDefaultLimitParams(req);
		BaseResponse baseResponse = TransactionService.getInstance().getAllTransactions(start, limit);
		resp.send(baseResponse.getStatus(), DataConversionUtil.make().toJson(baseResponse));
		return 0;
	}
}
