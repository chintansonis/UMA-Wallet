package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.umawallet.R;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.Functions;

/**
 * Created by chintans on 22-02-2018.
 */

public class ForgotPasswordActivity extends BaseActivity {
    private com.umawallet.custom.MyEditTextWithCloseBtn edtEmail;
    private com.umawallet.custom.TfTextView txtSubmit;

    public static void launchForgetPasswordActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, ForgotPasswordActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setShowBackMessage(false);
        init();
    }

    private void init() {
        this.txtSubmit = (TfTextView) findViewById(R.id.txtSubmit);
        this.edtEmail = (MyEditTextWithCloseBtn) findViewById(R.id.edtEmail);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Functions.showToast(ForgotPasswordActivity.this, getString(R.string.please_enter_email));
                } else if (!Functions.emailValidation(edtEmail.getText().toString().trim())) {
                    Functions.showToast(ForgotPasswordActivity.this, getString(R.string.please_enter_valid_email));
                } else {
                    if (Functions.isConnected(ForgotPasswordActivity.this)) {
                        callForgotPasswordApi();
                    } else {
                        Functions.showToast(ForgotPasswordActivity.this, getResources().getString(R.string.err_no_internet_connection));
                    }

                }*/
            }
        });
    }

}
