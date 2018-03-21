package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.umawallet.R;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;

/**
 * Created by chintans on 21-03-2018.
 */

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    private static DashBoardActivity dashboardActivity;
    private android.widget.LinearLayout llAppBar;
    private android.widget.FrameLayout contentFrame;

    /**
     * Launch activity.
     *
     * @param activity the instance of base activity
     */
    public static void launchDashboradActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, DashBoardActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivity = this;
        init();
    }

    private void init() {
        this.contentFrame = (FrameLayout) findViewById(R.id.contentFrame);
        this.llAppBar = (LinearLayout) findViewById(R.id.llAppBar);
        findViewById(R.id.llFooterSettings).setOnClickListener(this);
        findViewById(R.id.llFooterState).setOnClickListener(this);
        findViewById(R.id.llFooterHome).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llFooterHome:
                loadBottomUI(AppConstants.FOOTER_HOME);
                break;
            case R.id.llFooterSettings:
                loadBottomUI(AppConstants.FOOTER_SETTINGS);
                break;
            case R.id.llFooterState:
                loadBottomUI(AppConstants.FOOTER_STATES);
                break;
        }
    }
}
