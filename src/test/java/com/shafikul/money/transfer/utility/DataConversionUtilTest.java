package com.shafikul.money.transfer.utility;

import com.google.gson.Gson;
import net.freeutils.httpserver.HTTPServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataConversionUtilTest {

    @InjectMocks
    private DataConversionUtil dataConversionUtil;

    @Mock
    private HTTPServer.Response response;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMake() {
        Assert.assertTrue(dataConversionUtil.make() instanceof Gson);
    }

    @Test(expected = NullPointerException.class)
    public void testSetHeaders() {
        dataConversionUtil.setHeader(response);
        Assert.assertNotNull(response.getHeaders().get(Constants.contentType));
    }

    @Test
    public void testStreamToString() throws IOException {
        String test = "this is a test";
        byte[] data = test.getBytes();
        InputStream inputStream = new ByteArrayInputStream(data);
        Assert.assertEquals(test, dataConversionUtil.streamToString(inputStream));
    }
}
