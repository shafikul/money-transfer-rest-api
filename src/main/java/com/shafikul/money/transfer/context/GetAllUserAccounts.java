package com.shafikul.money.transfer.context;

import java.io.IOException;

import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.service.AccountService;
import com.shafikul.money.transfer.utility.DataConversionUtil;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import net.freeutils.httpserver.HTTPServer;

public class GetAllUserAccounts implements HTTPServer.ContextHandler {
	public int serve(HTTPServer.Request req, HTTPServer.Response resp) throws IOException {
		DataConversionUtil.setHeader(resp);
		int start = ValidatorUtil.checkOrGetDefaultStartParams(req);
		int limit = ValidatorUtil.checkOrGetDefaultLimitParams(req);
		BaseResponse result = AccountService.getInstance().getAllAccounts(start, limit);
		resp.send(result.getStatus(), DataConversionUtil.make().toJson(result));
		return 0;
	}
}
