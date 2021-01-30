package com.tianshuo.framework.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.tianshuo.framework.Invoke;
import okhttp3.*;

import java.io.IOException;

public class HttpClient {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public Object send (Invoke invoke) {
        String msg = JSONObject.toJSONString(invoke);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(msg, JSON);
        Request request = new Request.Builder()
                .url("http://localhost:8000")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
