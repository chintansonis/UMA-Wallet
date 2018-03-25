package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.umawallet.R;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;
import com.umawallet.ui.BaseActivity;
import com.umawallet.ui.ChnagePassowordActivity;
import com.umawallet.ui.LoginLandingActivity;

/**
 * Created by Shriji on 3/25/2018.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private com.umawallet.custom.TfTextView tvprofile;
    private com.umawallet.custom.TfTextView tvchangepassword;
    private com.umawallet.custom.TfTextView tvchangecurrency;
    private android.widget.CheckBox chkINR;
    private android.widget.RelativeLayout rlINr;
    private android.widget.CheckBox chkDollor;
    private android.widget.RelativeLayout rlDollor;
    private android.support.v7.widget.CardView cardViewCurrency;
    private com.umawallet.custom.TfTextView tvupdateemail;
    private com.umawallet.custom.TfTextView tvlogout;
    private com.umawallet.custom.TfTextView tvsendverificationemail;


    @SuppressLint({"ValidFragment", "Unused"})
    private SettingFragment() {
    }

    @SuppressLint("ValidFragment")
    private SettingFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new SettingFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init(view);
    }

    private void init(View view) {
        this.tvsendverificationemail = (TfTextView) view.findViewById(R.id.tv_send_verification_email);
        this.tvlogout = (TfTextView) view.findViewById(R.id.tv_logout);
        this.tvupdateemail = (TfTextView) view.findViewById(R.id.tv_update_email);
        this.cardViewCurrency = (CardView) view.findViewById(R.id.cardViewCurrency);
        this.rlDollor = (RelativeLayout) view.findViewById(R.id.rlDollor);
        this.chkDollor = (CheckBox) view.findViewById(R.id.chkDollor);
        this.rlINr = (RelativeLayout) view.findViewById(R.id.rlINr);
        this.chkINR = (CheckBox) view.findViewById(R.id.chkINR);
        this.tvchangecurrency = (TfTextView) view.findViewById(R.id.tv_change_currency);
        this.tvchangepassword = (TfTextView) view.findViewById(R.id.tv_change_password);
        this.tvprofile = (TfTextView) view.findViewById(R.id.tv_profile);
        tvchangecurrency.setOnClickListener(this);
        tvupdateemail.setOnClickListener(this);
        tvprofile.setOnClickListener(this);
        tvchangepassword.setOnClickListener(this);
        tvlogout.setOnClickListener(this);
        rlDollor.setOnClickListener(this);
        rlINr.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_update_email:
                cardViewCurrency.setVisibility(View.GONE);
                Functions.showToast(getBaseActivity(), getBaseActivity().getString(R.string.this_feature_under_dev));
                break;
            case R.id.tv_profile:
                cardViewCurrency.setVisibility(View.GONE);
                Functions.showToast(getBaseActivity(), getBaseActivity().getString(R.string.this_feature_under_dev));
                break;
            case R.id.tv_change_currency:
                if (cardViewCurrency.getVisibility() == View.VISIBLE) {
                    cardViewCurrency.setVisibility(View.GONE);
                } else {
                    cardViewCurrency.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_send_verification_email:
                cardViewCurrency.setVisibility(View.GONE);
                break;
            case R.id.tv_logout:
                cardViewCurrency.setVisibility(View.GONE);
                logout();
                break;
            case R.id.tv_change_password:
                cardViewCurrency.setVisibility(View.GONE);
                ChnagePassowordActivity.launchChangePasswordActivity(getBaseActivity());
                break;
            case R.id.rlDollor:
                Functions.showToast(getBaseActivity(), getBaseActivity().getString(R.string.this_feature_under_dev));
                break;
            case R.id.rlINr:
                Functions.showToast(getBaseActivity(), getBaseActivity().getString(R.string.this_feature_under_dev));
                break;

        }
    }

    private void logout() {
        Preferences.getInstance(getBaseActivity()).clearAll();
        Functions.fireIntentWithClearFlagWithWithPendingTransition(getBaseActivity(), LoginLandingActivity.class);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
