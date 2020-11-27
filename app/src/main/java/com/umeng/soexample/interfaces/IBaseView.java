package com.umeng.soexample.interfaces;

import android.widget.Toast;

public interface IBaseView {
    void tips(String tip);
    void loading(int visible);

    void showToast(String msg, int time);

}
