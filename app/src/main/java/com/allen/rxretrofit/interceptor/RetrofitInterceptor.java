package com.allen.rxretrofit.interceptor;

import android.util.Log;

import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by lkz on 2016/11/3.
 */

public class RetrofitInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        String authHeader = "this is a test";
        Request request = chain.request()
                .newBuilder()
                .header("ttm_token", authHeader)
                .header("version", "v2.2")
                .build();

        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                //Couldn't decode the response body; charset is likely malformed.
                return response;
            }
        }

        if (!isPlaintext(buffer)) {
            return response;
        }

        if (contentLength != 0) {
            String result = buffer.clone().readString(charset);
            try {
                JSONObject object = new JSONObject(result);
                if (object.has("code")) {
                    int code = object.getInt("code");
                    switch (code) {
                        case 1104:
                            break;
                        case 1105:
                        case 1106:
                        case 1108:
                            break;
                        case 1107:
                            break;
                        case 1200:
                            break;
                        case 1102:
                            Log.d("拦截结果", "还没有登录");
                            break;
                        case 3004:
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return response;
    }


    private static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
