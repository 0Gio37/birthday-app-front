package com.georges.android.birthday_app_front.utils;

public interface ApiCallBack {
    void fail(String json);
    void success(String json);
}
