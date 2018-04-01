package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.umawallet.R;
import com.umawallet.api.BaseResponse;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.RequeslUpdateProfile;
import com.umawallet.api.responsepojos.RequestEmailUpdate;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;
import com.umawallet.ui.BaseActivity;
import com.umawallet.ui.ChnagePassowordActivity;
import com.umawallet.ui.LoginActivity;
import com.umawallet.ui.LoginLandingActivity;
import com.umawallet.ui.WalletActvity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        view.findViewById(R.id.tv_wallet).setOnClickListener(this);
        tvlogout.setOnClickListener(this);
        rlDollor.setOnClickListener(this);
        rlINr.setOnClickListener(this);
        chkINR.setOnClickListener(this);
        chkDollor.setOnClickListener(this);
        if (Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_CURRENCY).equalsIgnoreCase(AppConstants.CURRENCY_INR)) {
            chkINR.setChecked(true);
            chkDollor.setChecked(false);
        } else {
            chkDollor.setChecked(true);
            chkINR.setChecked(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_update_email:
                cardViewCurrency.setVisibility(View.GONE);
                showUpdateEmailDialogue(true);
                break;
            case R.id.tv_profile:
                cardViewCurrency.setVisibility(View.GONE);
                showUpdateEmailDialogue(false);
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
                if (!chkDollor.isChecked()) {
                    chkDollor.setChecked(true);
                }
                chkINR.setChecked(false);
                Preferences.getInstance(getBaseActivity()).setString(Preferences.KEY_CURRENCY, AppConstants.CURRENCY_USD);
                break;
            case R.id.rlINr:
                if (!chkINR.isChecked()) {
                    chkINR.setChecked(true);
                }
                chkDollor.setChecked(false);
                Preferences.getInstance(getBaseActivity()).setString(Preferences.KEY_CURRENCY, AppConstants.CURRENCY_INR);
                break;
            case R.id.chkDollor:
                if (!chkDollor.isChecked()) {
                    chkDollor.setChecked(true);
                }
                chkINR.setChecked(false);
                Preferences.getInstance(getBaseActivity()).setString(Preferences.KEY_CURRENCY, AppConstants.CURRENCY_USD);
                break;
            case R.id.chkINR:
                if (!chkINR.isChecked()) {
                    chkINR.setChecked(true);
                }
                chkDollor.setChecked(false);
                Preferences.getInstance(getBaseActivity()).setString(Preferences.KEY_CURRENCY, AppConstants.CURRENCY_INR);
                break;
            case R.id.tv_wallet:
                WalletActvity.launchWalletActivity(getBaseActivity());
                break;

        }
    }

    private void showUpdateEmailDialogue(final boolean isEmail) {
        final Dialog dialog = new Dialog(getBaseActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_update_email);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        TfTextView txtSendFeedback = (TfTextView) dialog.findViewById(R.id.txtSubmit);
        TfTextView title = (TfTextView) dialog.findViewById(R.id.title);
        final MyEditTextWithCloseBtn edtEmail = (MyEditTextWithCloseBtn) dialog.findViewById(R.id.edtEmail);
        final MyEditTextWithCloseBtn edtName = (MyEditTextWithCloseBtn) dialog.findViewById(R.id.edtName);
        dialog.getWindow().setAttributes(lp);
        if (isEmail) {
            title.setText(getBaseActivity().getString(R.string.update_email));
            edtEmail.setVisibility(View.VISIBLE);
            edtName.setVisibility(View.GONE);
        } else {
            edtEmail.setVisibility(View.GONE);
            edtName.setVisibility(View.VISIBLE);
            title.setText(getBaseActivity().getString(R.string.update_profile));
        }
        txtSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmail) {
                    if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                        Functions.showToast(getBaseActivity(), getString(R.string.please_enter_email));
                    } else if (!Functions.emailValidation(edtEmail.getText().toString().trim())) {
                        Functions.showToast(getBaseActivity(), getString(R.string.please_enter_valid_email));
                    } else {
                        callUpdateEmailApi(dialog, edtEmail);
                    }
                } else {
                    if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
                        Functions.showToast(getBaseActivity(), getString(R.string.please_enter_name));
                    } else {
                        callUpdateProfileApi(dialog, edtName);
                    }
                }
            }
        });
        dialog.show();
    }

    private void callUpdateProfileApi(final Dialog dialog, MyEditTextWithCloseBtn edtName) {
        if (Functions.isConnected(getBaseActivity())) {
            getBaseActivity().showProgressDialog(false);
            RequeslUpdateProfile requeslUpdateProfile = new RequeslUpdateProfile();
            requeslUpdateProfile.setFullName(edtName.getText().toString().trim());
            requeslUpdateProfile.setUserId(Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_USER_ID));
            RestClient.get().updateProfileApi(requeslUpdateProfile).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    getBaseActivity().hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(getBaseActivity(), response.body().getMessage());
                            dialog.dismiss();
                        } else {
                            Functions.showToast(getBaseActivity(), response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    getBaseActivity().hideProgressDialog();
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            });
        }

    }

    private void callUpdateEmailApi(final Dialog dialog, MyEditTextWithCloseBtn edtEmail) {
        if (Functions.isConnected(getBaseActivity())) {
            getBaseActivity().showProgressDialog(false);
            RequestEmailUpdate requestEmailUpdate = new RequestEmailUpdate();
            requestEmailUpdate.setEmail(edtEmail.getText().toString().trim());
            requestEmailUpdate.setUserId(Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_USER_ID));
            RestClient.get().updateEmailApi(requestEmailUpdate).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    getBaseActivity().hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(getBaseActivity(), response.body().getMessage());
                            dialog.dismiss();
                            Preferences.getInstance(getBaseActivity()).clearAll();
                            Functions.fireIntentWithClearFlagWithWithPendingTransition(getBaseActivity(), LoginActivity.class);
                            getBaseActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            Functions.showToast(getBaseActivity(), response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    getBaseActivity().hideProgressDialog();
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            });
        }
    }

    private void logout() {
        Preferences.getInstance(getBaseActivity()).clearAll();
        Functions.fireIntentWithClearFlagWithWithPendingTransition(getBaseActivity(), LoginLandingActivity.class);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
