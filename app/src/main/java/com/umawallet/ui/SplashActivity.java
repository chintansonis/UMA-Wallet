package com.umawallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.umawallet.R;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private com.umawallet.custom.TfTextView txtAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setShowBackMessage(false);
        init();

    }

    private void init() {
        txtAppName = (TfTextView) findViewById(R.id.txtAppName);
     //   textAnimation();
        appHandler();
    }

    private void textAnimation() {
        Animation animSequential = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.sequential);
        txtAppName.startAnimation(animSequential);
    }

    private void appHandler() {
        new CountDownTimer(1800, 1800) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (Preferences.getInstance(SplashActivity.this).getBoolean(Preferences.KEY_IS_AUTO_LOGIN)) {
                    if (Functions.isConnected(SplashActivity.this)) {
                        callGetUserAddressApi();
                    } else {
                        Functions.showToast(SplashActivity.this, getResources().getString(R.string.err_no_internet_connection));
                    }
                } else {
                    Functions.fireIntent(SplashActivity.this, LoginLandingActivity.class);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        }.start();
    }

    private void callGetUserAddressApi() {
        RestClient.get().getUserAddressApi(Preferences.getInstance(SplashActivity.this).getString(Preferences.KEY_USER_ID), Preferences.getInstance(SplashActivity.this).getString(Preferences.KEY_CURRENCY)).enqueue(new Callback<ResponseGeUserAddress>() {
            @Override
            public void onResponse(Call<ResponseGeUserAddress> call, Response<ResponseGeUserAddress> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if (response.body().getAddressMaster() != null) {
                            if (response.body().getAddressMaster().size() > 0) {
                                DashBoardActivity.launchDashboradActivity(SplashActivity.this);
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
                Functions.showToast(SplashActivity.this, t.getMessage());
            }
        });
    }

    private void firetoAddNewWallet() {
        Intent intent = new Intent(SplashActivity.this, AddUpdateWalletDetailActivity.class);
        intent.putExtra("isFrom", "From Login");
        Functions.fireIntent(SplashActivity.this, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
