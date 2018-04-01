package com.umawallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.umawallet.R;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.LoginRequest;
import com.umawallet.api.responsepojos.LoginResponse;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.api.responsepojos.UserDetails;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    callLoginApi();
                }
                break;
            case R.id.txtForgotPassword:
                ForgotPasswordActivity.launchForgetPasswordActivity(LoginActivity.this);
                break;
        }
    }

    private void callLoginApi() {
        if (Functions.isConnected(LoginActivity.this)) {
            showProgressDialog(false);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(edtEmail.getText().toString().trim());
            loginRequest.setPassword(edtPassword.getText().toString());
            RestClient.get().loginApi(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            onLoginResponse(response.body().getUserDetails());
                        } else {
                            Functions.showToast(LoginActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(LoginActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(LoginActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(LoginActivity.this, getString(R.string.err_no_internet_connection));
        }
    }

    private void onLoginResponse(UserDetails userDetails) {
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_ID, userDetails.getUserId());
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_CURRENCY, AppConstants.CURRENCY_USD);
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_LOGIN_PASS_WORD, edtPassword.getText().toString().trim().trim());
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_LOGIN_EMAIL, edtEmail.getText().toString().trim().trim());
        Preferences.getInstance(LoginActivity.this).setBoolean(Preferences.KEY_IS_AUTO_LOGIN, true);
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(userDetails));
        LoginLandingActivity.loginLandingActivity.finish();
        if (Functions.isConnected(LoginActivity.this)) {
            callGetUserAddressApi();
        } else {
            Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }
    private void callGetUserAddressApi() {
        RestClient.get().getUserAddressApi(Preferences.getInstance(LoginActivity.this).getString(Preferences.KEY_USER_ID), Preferences.getInstance(LoginActivity.this).getString(Preferences.KEY_CURRENCY)).enqueue(new Callback<ResponseGeUserAddress>() {
            @Override
            public void onResponse(Call<ResponseGeUserAddress> call, Response<ResponseGeUserAddress> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if (response.body().getAddressMaster() != null) {
                            if (response.body().getAddressMaster().size() > 0) {
                                DashBoardActivity.launchDashboradActivity(LoginActivity.this);
                            } else {
                                firetoAddNewWallet();
                            }
                        } else {
                            firetoAddNewWallet();
                        }
                    } else {
                        firetoAddNewWallet();
                    }
                } else {
                    firetoAddNewWallet();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeUserAddress> call, Throwable t) {
                firetoAddNewWallet();
                Functions.showToast(LoginActivity.this, t.getMessage());
            }
        });
    }

    private void firetoAddNewWallet() {
        Intent intent = new Intent(LoginActivity.this, AddUpdateWalletDetailActivity.class);
        intent.putExtra("isFrom", "From Login");
        Functions.fireIntent(LoginActivity.this, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
