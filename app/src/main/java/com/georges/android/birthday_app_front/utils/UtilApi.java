package com.georges.android.birthday_app_front.utils;

import android.os.Handler;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UtilApi {

    public static final String URL_LOGIN = "http://10.0.2.2:8080/login";
    public static final String URL_BIRTHDAY = "http://10.0.2.2:8080/users";
    public static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    //method get
    public static void get(String url, final ApiCallBack callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.fail("onFailure error get : timestamp server or connectivity");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful())
                    callback.success(response.body().string());
                else {
                    callback.fail("onResponse error post: api response not valid");
                }
            }
        });
    }

    //method post
    public static void post(String url, Map<String, String> map, final ApiCallBack callback) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBody.addFormDataPart(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        Log.d("request url", url);
        Log.d("request requestbody", requestBody.build().toString());
        Log.d("request", request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.fail("onFailure error post : timestamp server or connectivity" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.d("response", response.toString());
                if (response.isSuccessful())
                    callback.success(response.body().string());
                else {
                    callback.fail("onResponse error post: api response not valid");
                }
            }
        });
    }

    //TODO method PUT to create
    //TODO method DELETE to create
    public static void delete(String url, String birthdayId, final ApiCallBack callback) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("birthday-ID", birthdayId);
        Request request = new Request.Builder()
                .url(url)
                .delete(requestBody.build())
                .build();
        Log.d("request url", url);
        Log.d("request", request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.fail("onFailure error post : timestamp server or connectivity" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.d("response", response.toString());
                if (response.isSuccessful())
                    callback.success(response.body().string());
                else {
                    callback.fail("onResponse error post: api response not valid");
                }
            }
        });
    }







}
