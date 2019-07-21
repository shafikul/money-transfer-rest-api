package com.shafikul.money.transfer.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;

import net.freeutils.httpserver.HTTPServer;

public class DataConversionUtil {

	public static String streamToString(InputStream inputStream) throws IOException {
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(inputStream, "UTF-8");
		for (;;) {
			int rsz = in.read(buffer, 0, buffer.length);
			if (rsz < 0)
				break;
			out.append(buffer, 0, rsz);
		}
		return out.toString();
	}

	public static Gson make() {
		return new Gson().newBuilder().setPrettyPrinting().create();
	}

	public static void setHeader(HTTPServer.Response resp) {
		resp.getHeaders().add(Constants.contentType, Constants.contentValue);
	}
}
