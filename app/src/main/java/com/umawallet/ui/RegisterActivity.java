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

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private com.umawallet.custom.MyEditTextWithCloseBtn edtName;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtEmail;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtPassword;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtConfirmPassword;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethAddress;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethkey;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethnickName;
    private com.umawallet.custom.TfTextView txtRegister;

    /**
     * Launch activity.
     *
     * @param activity the instance of base activity
     */
    public static void launchRegisterActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, RegisterActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setShowBackMessage(false);
        init();
    }

    private void init() {
        txtRegister = (TfTextView) findViewById(R.id.txtRegister);
        edtethnickName = (MyEditTextWithCloseBtn) findViewById(R.id.edtethnickName);
        edtethkey = (MyEditTextWithCloseBtn) findViewById(R.id.edtethkey);
        edtethAddress = (MyEditTextWithCloseBtn) findViewById(R.id.edtethAddress);
        edtConfirmPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtConfirmPassword);
        edtPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtPassword);
        edtEmail = (MyEditTextWithCloseBtn) findViewById(R.id.edtEmail);
        edtName = (MyEditTextWithCloseBtn) findViewById(R.id.edtName);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRegister:
                if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.please_enter_name));
                } else if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.please_enter_email));
                } else if (!Functions.emailValidation(edtEmail.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.please_enter_valid_email));
                } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.please_enter_password));
                } else if (TextUtils.isEmpty(edtConfirmPassword.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.please_enter_confirm_password));
                } else if (!edtPassword.getText().toString().trim().equalsIgnoreCase(edtConfirmPassword.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.err_pass_not_match));
                } else {
                    DashBoardActivity.launchDashboradActivity(RegisterActivity.this);
                    finish();
                }
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
