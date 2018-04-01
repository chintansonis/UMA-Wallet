package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.umawallet.R;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.LoginResponse;
import com.umawallet.api.responsepojos.RequestRegister;
import com.umawallet.api.responsepojos.ResponseGetWallet;
import com.umawallet.api.responsepojos.UserDetails;
import com.umawallet.api.responsepojos.Wallet;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private com.umawallet.custom.MyEditTextWithCloseBtn edtAddharNumber;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethnickName;
    private com.umawallet.custom.TfTextView txtRegister;
    private ArrayList<Wallet> walletArrayList = new ArrayList<>();

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
        getWalletApi();
    }

    private void getWalletApi() {
        if (Functions.isConnected(RegisterActivity.this)) {
            showProgressDialog(false);
            RestClient.get().getWalletApi().enqueue(new Callback<ResponseGetWallet>() {
                @Override
                public void onResponse(Call<ResponseGetWallet> call, Response<ResponseGetWallet> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            walletArrayList.addAll(response.body().getWallet());
                        } else {
                            Functions.showToast(RegisterActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetWallet> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(RegisterActivity.this, getString(R.string.err_no_internet_connection));
        }
    }

    private void init() {
        txtRegister = (TfTextView) findViewById(R.id.txtRegister);
        edtethnickName = (MyEditTextWithCloseBtn) findViewById(R.id.edtethnickName);
        edtethkey = (MyEditTextWithCloseBtn) findViewById(R.id.edtethkey);
        edtAddharNumber = (MyEditTextWithCloseBtn) findViewById(R.id.edtAddharNumber);
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
                }else if(TextUtils.isEmpty(edtAddharNumber.getText().toString().trim())&&TextUtils.isEmpty(edtethnickName.getText().toString().trim())&&TextUtils.isEmpty(edtethAddress.getText().toString().trim())&&TextUtils.isEmpty(edtethkey.getText().toString().trim())){
                    callRegisterApi();
                }
                else if(!isAnyFieldEmptyWallet()){
                    Functions.showToast(RegisterActivity.this, getString(R.string.err_mandatory));
                }
                else {
                    callRegisterApi();
                }
                break;

        }
    }
    private boolean isAnyFieldEmptyWallet()
    {
        boolean isEmpty = !edtethkey.getText().toString().trim().isEmpty();
        isEmpty = isEmpty && !edtethAddress.getText().toString().trim().isEmpty();
        isEmpty = isEmpty && !edtethnickName.getText().toString().trim().isEmpty();
        isEmpty = isEmpty && !edtAddharNumber.getText().toString().trim().isEmpty();
        return isEmpty;
    }
    private void callRegisterApi() {
        if (Functions.isConnected(RegisterActivity.this)) {
            showProgressDialog(false);
            RequestRegister requestRegister = new RequestRegister();
            requestRegister.setEmail(edtEmail.getText().toString().trim());
            requestRegister.setPassword(edtPassword.getText().toString());
            requestRegister.setFullName(edtName.getText().toString());
            requestRegister.setAdharNumber("");
            requestRegister.setWalletAddress("");
            requestRegister.setWalletPublicKey("");
            requestRegister.setWalletNickName("");
            requestRegister.setWalletId(walletArrayList.get(0).getWalletId()+"");
            RestClient.get().RegisterApi(requestRegister).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            onLoginResponse(response.body().getUserDetails());
                        } else {
                            Functions.showToast(RegisterActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(RegisterActivity.this, getString(R.string.err_no_internet_connection));
        }
    }

    private void onLoginResponse(UserDetails userDetails) {
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_ID, userDetails.getUserId());
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_LOGIN_PASS_WORD, edtPassword.getText().toString().trim().trim());
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_LOGIN_EMAIL, edtEmail.getText().toString().trim().trim());
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(userDetails));
        Functions.fireIntent(RegisterActivity.this, LoginActivity.class);
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
