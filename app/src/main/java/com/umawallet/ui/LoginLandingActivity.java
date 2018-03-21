package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.umawallet.R;
import com.umawallet.custom.TfTextView;

/**
 * Created by chintans on 21-03-2018.
 */

public class LoginLandingActivity extends BaseActivity implements View.OnClickListener {
    public static LoginLandingActivity loginLandingActivity;
    private com.umawallet.custom.TfTextView txtNewUser;
    private com.umawallet.custom.TfTextView txtLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        loginLandingActivity = this;
        setShowBackMessage(false);
        init();
    }

    private void init() {
        txtLogin = (TfTextView) findViewById(R.id.txtLogin);
        txtNewUser = (TfTextView) findViewById(R.id.txtNewUser);
        txtLogin.setOnClickListener(this);
        txtNewUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtLogin:
                LoginActivity.launchLoginActivity(LoginLandingActivity.this);
                break;
            case R.id.txtNewUser:
                RegisterActivity.launchRegisterActivity(LoginLandingActivity.this);
                break;
        }
    }
}
