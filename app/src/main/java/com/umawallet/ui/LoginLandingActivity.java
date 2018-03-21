package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.umawallet.R;

/**
 * Created by chintans on 21-03-2018.
 */

public class LoginLandingActivity extends BaseActivity{
    public static LoginLandingActivity loginLandingActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        loginLandingActivity = this;
        init();
    }

    private void init() {

    }
}
