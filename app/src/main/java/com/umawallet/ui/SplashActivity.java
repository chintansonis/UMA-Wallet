package com.umawallet.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.umawallet.R;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.Functions;

public class SplashActivity extends AppCompatActivity {

    private com.umawallet.custom.TfTextView txtAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

    }

    private void init() {
        txtAppName = (TfTextView) findViewById(R.id.txtAppName);
        textAnimation();
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
                Functions.fireIntent(SplashActivity.this, LoginLandingActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }.start();
    }
}
