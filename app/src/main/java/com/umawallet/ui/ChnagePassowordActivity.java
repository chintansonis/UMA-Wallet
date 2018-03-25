package com.umawallet.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.umawallet.R;
import com.umawallet.api.BaseResponse;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.RequestPassword;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 3/25/2018.
 */

public class ChnagePassowordActivity extends BaseActivity {


    private com.umawallet.custom.MyEditTextWithCloseBtn edtPassword;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtNewPassword;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtConfirmPassword;
    private com.umawallet.custom.TfTextView txtLogin;

    public static void launchChangePasswordActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, ChnagePassowordActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_password);
        setShowBackMessage(false);
        init();

    }

    private void init() {
        this.txtLogin = (TfTextView) findViewById(R.id.txtLogin);
        this.edtConfirmPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtConfirmPassword);
        this.edtNewPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtNewPassword);
        this.edtPassword = (MyEditTextWithCloseBtn) findViewById(R.id.edtPassword);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.set_old_pass));
                } else if (!edtPassword.getText().toString().trim().equalsIgnoreCase(Preferences.getInstance(ChnagePassowordActivity.this).getString(Preferences.KEY_USER_LOGIN_PASS_WORD))) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.entered_wrong_pass));
                } else if (TextUtils.isEmpty(edtNewPassword.getText().toString().trim())) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.set_new_pass));
                } else if (TextUtils.isEmpty(edtConfirmPassword.getText().toString().trim())) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.set_new_confirm));
                } else if (!edtNewPassword.getText().toString().trim().equalsIgnoreCase(edtConfirmPassword.getText().toString().trim())) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.err_pass_not_match));
                } else if (edtPassword.getText().toString().trim().equalsIgnoreCase(edtNewPassword.getText().toString().trim())) {
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.password_not_be_same));
                } else {
                    callRegistrationPasswordApi();
                }
            }
        });
    }

    private void callRegistrationPasswordApi() {
        if (Functions.isConnected(ChnagePassowordActivity.this)) {
            showProgressDialog(false);
            final RequestPassword requestPassword = new RequestPassword();
            requestPassword.setUserId(Preferences.getInstance(ChnagePassowordActivity.this).getString(Preferences.KEY_USER_ID));
            requestPassword.setNewPassword(edtConfirmPassword.getText().toString());
            requestPassword.setOldPassword(edtPassword.getText().toString());
            RestClient.get().updatePasswordApi(requestPassword).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            //Functions.showToast(ChnagePassowordActivity.this, response.body().getMessage());
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            Functions.showToast(ChnagePassowordActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(ChnagePassowordActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(ChnagePassowordActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(ChnagePassowordActivity.this, getString(R.string.err_no_internet_connection));
        }
    }
}
