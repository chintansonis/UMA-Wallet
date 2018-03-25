package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.umawallet.R;
import com.umawallet.fragment.DashBoardFragment;
import com.umawallet.fragment.SettingFragment;
import com.umawallet.fragment.StateChartFragment;
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
            activity.finish();
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivity = this;
        init();
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int position) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragments().clear();
        if (position == 0) {
            setHeaderTitle(getString(R.string.app_name));
            Fragment fragmentToPush = DashBoardFragment.getFragment(this);
            pushAddFragments(fragmentToPush, false, true);
        }
        loadBottomUI(AppConstants.FOOTER_HOME);
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
        switch (v.getId()) {
            case R.id.llFooterHome:
                selectItem(0);
                break;
            case R.id.llFooterSettings:
                setHeaderTitle(getString(R.string.settings));
                Fragment fragmentToPushSettings = SettingFragment.getFragment(this);
                pushAddFragments(fragmentToPushSettings, false, true);
                loadBottomUI(AppConstants.FOOTER_SETTINGS);
                break;
            case R.id.llFooterState:
                setHeaderTitle(getString(R.string.states));
                Fragment fragmentToPushMyCoupns = StateChartFragment.getFragment(this);
                pushAddFragments(fragmentToPushMyCoupns, false, true);
                loadBottomUI(AppConstants.FOOTER_STATES);
                break;
        }
    }
}
