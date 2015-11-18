package com.pb.projectbuilder.Connecter;

import com.loopj.android.http.*;

/**
 * Created by jongchan on 2015-11-02.
 */
public class HttpClient {

      private static final String BASE_URL = "http://192.168.0.84:8080/ProjectBuilderServer/pb?cmd=";  //집 : 192.168.0.84


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    }


