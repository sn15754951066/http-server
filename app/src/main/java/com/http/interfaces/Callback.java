package com.http.interfaces;

public interface Callback<T> {

    void fail(String msg);

    void success(T t);

}
