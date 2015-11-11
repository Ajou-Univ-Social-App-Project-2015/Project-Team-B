package com.pb.projectbuilder;

import android.content.Context;

import com.loopj.android.http.*;

import org.apache.http.HttpEntity;

/**
 * Created by jongchan on 2015-11-02.
 */
public class HttpClient {

      private static final String BASE_URL = "http://192.168.0.83:8080/ProjectBuilderServer/pb?cmd=";  //집
    //private static final String BASE_URL = "http://192.168.0.133:8080/ProjectBuilderServer/pb?cmd=";


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


