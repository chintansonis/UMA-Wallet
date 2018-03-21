package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.umawallet.R;

/**
 * Created by chintans on 21-03-2018.
 */

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    private static DashBoardActivity dashboardActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivity = this;
    }


    @Override
    public void onClick(View v) {

    }
}
