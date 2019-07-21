package com.shafikul.money.transfer;

import com.shafikul.money.transfer.context.GetAllTransactions;
import com.shafikul.money.transfer.context.GetAllUserAccounts;
import com.shafikul.money.transfer.context.GetUserAccountDetails;
import com.shafikul.money.transfer.context.MoneyTransferFromAccountToAccount;
import com.shafikul.money.transfer.context.OpenUserAccount;
import com.shafikul.money.transfer.context.SendMoneyToAccount;
import com.shafikul.money.transfer.context.WithDrawMoneyFromAccount;
import com.shafikul.money.transfer.utility.Constants;

import net.freeutils.httpserver.HTTPServer;

public class Main {

	/**
	 * Starts a stand-alone HTTP server, serving files from disk.
	 */
	public static void main(String[] args) {
		try {
			int port = 8888;
			HTTPServer server = new HTTPServer(port);
			HTTPServer.VirtualHost host = server.getVirtualHost(null);
			host.setAllowGeneratedIndex(false);

			// all API EndPoint
			host.addContext(Constants.ALL_ACCOUNT_PATH, new GetAllUserAccounts());
			host.addContext(Constants.ACCOUNT_DETAILS_PATH, new GetUserAccountDetails());
			host.addContext(Constants.OPENT_ACCOUNT_PATH, new OpenUserAccount(), Constants.REQUEST_TYPE_POST);
			host.addContext(Constants.TRANSFER_MONEY_PATH, new MoneyTransferFromAccountToAccount(),
					Constants.REQUEST_TYPE_POST);
			host.addContext(Constants.DEPOSIT_MONEY_PATH, new SendMoneyToAccount(), Constants.REQUEST_TYPE_POST);
			host.addContext(Constants.WITH_DRWA_MONEY_PATH, new WithDrawMoneyFromAccount(),
					Constants.REQUEST_TYPE_POST);
			host.addContext(Constants.ALL_TRANSATIONS_PATH, new GetAllTransactions());

			server.start();
			System.out.println("HTTPServer is listening on port " + port);
		} catch (Exception e) {
			System.err.println("error: " + e);
		}
	}

	public static void stopServer() {

	}
}
