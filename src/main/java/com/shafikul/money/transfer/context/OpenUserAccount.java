package com.shafikul.money.transfer.context;

import java.io.IOException;

import com.google.gson.Gson;
import com.shafikul.money.transfer.core.beans.AccountBean;
import com.shafikul.money.transfer.core.beans.BaseResponse;
import com.shafikul.money.transfer.core.enums.ResponseType;
import com.shafikul.money.transfer.service.AccountService;
import com.shafikul.money.transfer.utility.DataConversionUtil;
import com.shafikul.money.transfer.utility.Messages;
import com.shafikul.money.transfer.utility.Status;
import com.shafikul.money.transfer.utility.ValidatorUtil;

import net.freeutils.httpserver.HTTPServer;

public class OpenUserAccount implements HTTPServer.ContextHandler {
    public int serve(HTTPServer.Request req, HTTPServer.Response resp) throws IOException {
        DataConversionUtil.setHeader(resp);
        AccountBean account = new Gson().fromJson(DataConversionUtil.streamToString(req.getBody()), AccountBean.class);
        BaseResponse baseResponse;
        if (!ValidatorUtil.validate(account)) {
            baseResponse = BaseResponse.builder().type(ResponseType.ERROR).status(Status.BAD_REQUEST)
                    .message(Messages.INVALID_PAYLOAD).build();
            resp.send(baseResponse.getStatus(), DataConversionUtil.make().toJson(baseResponse));
            return 0;
        }
        baseResponse = AccountService.getInstance().openAccount(account);
        resp.send(baseResponse.getStatus(), DataConversionUtil.make().toJson(baseResponse));
        return 0;
    }
}
