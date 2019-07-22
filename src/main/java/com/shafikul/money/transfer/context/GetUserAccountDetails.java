package com.shafikul.money.transfer.context;

import java.io.IOException;

import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.enums.ResponseType;
import com.shafikul.money.transfer.service.AccountService;
import com.shafikul.money.transfer.utility.Constants;
import com.shafikul.money.transfer.utility.DataConversionUtil;
import com.shafikul.money.transfer.utility.Messages;
import com.shafikul.money.transfer.utility.Status;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import net.freeutils.httpserver.HTTPServer;

public class GetUserAccountDetails implements HTTPServer.ContextHandler {
    public int serve(HTTPServer.Request req, HTTPServer.Response resp) throws IOException {
        DataConversionUtil.setHeader(resp);
        BaseResponse response;
        Integer accountId = ValidatorUtil.validateAndGetIDParams(req);
        Integer recentTransactionLimit = ValidatorUtil.checkOrGetDefaultLimitParams(req);
        if (accountId.equals(Constants.INVALID_ID)) {
            response = BaseResponse.builder().type(ResponseType.ERROR).status(Status.UNPROCESSED_ENTITY)
                    .message(Messages.INVALID_PAYLOAD).build();
            resp.send(response.getStatus(), DataConversionUtil.make().toJson(response));
            return 0;
        }
        response = AccountService.getInstance().getAccountDetailsById(accountId, recentTransactionLimit);
        resp.send(response.getStatus(), DataConversionUtil.make().toJson(response));
        return 0;
    }
}
