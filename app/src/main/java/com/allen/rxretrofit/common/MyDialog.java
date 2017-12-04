package com.allen.rxretrofit.common;

import android.app.Dialog;
import android.content.Context;

import com.allen.rxretrofit.R;

public class MyDialog extends Dialog {


    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setContentView(R.layout.alert_dialog);
    }
}
