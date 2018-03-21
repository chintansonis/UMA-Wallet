package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.umawallet.R;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.Functions;

/**
 * Created by Shriji on 3/21/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private com.umawallet.custom.MyEditTextWithCloseBtn edtEmail;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtPassword;
    private com.umawallet.custom.TfTextView txtForgotPassword;
    private com.umawallet.custom.TfTextView txtLogin;

    /**
     * Launch activity.
     *
     * @param activity the instance of base activity
     */
    public static void launchLoginActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, LoginActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setShowBackMessage(false);
        init();
    }

    private void init() {
        txtLogin = (TfTextView) findViewById(R.id.txtLogin);
        txtForgotPassword = (TfTextView) findViewById(R.id.txtForgotPassword);
        edtPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtPassword);
        edtEmail = (MyEditTextWithCloseBtn) findViewById(R.id.edtEmail);
        txtLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtLogin:
                if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Functions.showToast(LoginActivity.this, getString(R.string.please_enter_email));
                } else if (!Functions.emailValidation(edtEmail.getText().toString().trim())) {
                    Functions.showToast(LoginActivity.this, getString(R.string.please_enter_valid_email));
                } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    Functions.showToast(LoginActivity.this, getString(R.string.please_enter_password));
                } else {
                    DashBoardActivity.launchDashboradActivity(LoginActivity.this);
                    finish();
                }
                break;
            case R.id.txtForgotPassword:
                ForgotPasswordActivity.launchForgetPasswordActivity(LoginActivity.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dofinish();
    }

    private void dofinish() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
