package com.umawallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.umawallet.R;
import com.umawallet.api.BaseResponse;
import com.umawallet.api.RequestAddWallet;
import com.umawallet.api.RequestUpdateWallet;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 4/1/2018.
 */

public class AddUpdateWalletDetailActivity extends BaseActivity {

    private com.umawallet.custom.MyEditTextWithCloseBtn edtethnickName;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethAddress;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtethkey;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtAddharNumber;
    private com.umawallet.custom.TfTextView txtRegister;
    private android.widget.ScrollView scrollView;
    private android.support.v7.widget.Toolbar toolbar;
    private TfTextView txtTitle, txtAddNewWallet;
    private String isFrom = "";
    private AddressMaster addressMaster;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        setShowBackMessage(false);
        initToolbar();
        init();
        if (getIntent() != null) {
            isFrom = getIntent().getStringExtra("isFrom");
            if (isFrom.equalsIgnoreCase("Add Wallet")) {
                txtTitle.setText(getString(R.string.add_new_wallet));
                txtRegister.setText("Add Wallet");
            } else if (isFrom.equalsIgnoreCase("Edit Wallet")) {
                addressMaster = (AddressMaster) getIntent().getSerializableExtra("addressMaster");
                txtTitle.setText(getString(R.string.edit_wallet));
                setData(addressMaster);
            } else {
                txtAddNewWallet.setVisibility(View.VISIBLE);
                txtAddNewWallet.setText("Skip");
                txtTitle.setText(getString(R.string.add_new_wallet));
                txtRegister.setText("Add Wallet");
                txtAddNewWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DashBoardActivity.launchDashboradActivity(AddUpdateWalletDetailActivity.this);
                    }
                });
            }
        }
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtRegister.getText().toString().equalsIgnoreCase("Add Wallet")) {
                    if (TextUtils.isEmpty(edtethnickName.getText().toString().trim())) {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_please_enter_nick_name));
                    } else if (TextUtils.isEmpty(edtethAddress.getText().toString().trim())) {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.errr_enter_address));
                    } else if (TextUtils.isEmpty(edtethkey.getText().toString().trim())) {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.enter_ethreum_key));
                    } else if (TextUtils.isEmpty(edtAddharNumber.getText().toString().trim())) {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.errr_enter_adhar));
                    } else if (edtAddharNumber.getText().toString().length() < 12) {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_valied_adhar));
                    } else {
                        addWalletApi();
                    }
                } else {
                    updateWalletApi();
                }
            }
        });
    }

    private void updateWalletApi() {
        if (Functions.isConnected(AddUpdateWalletDetailActivity.this)) {
            showProgressDialog(false);
            RequestUpdateWallet requestUpdateWallet = new RequestUpdateWallet();
            requestUpdateWallet.setUserId(Preferences.getInstance(AddUpdateWalletDetailActivity.this).getString(Preferences.KEY_USER_ID));
            requestUpdateWallet.setAdharNumber(edtAddharNumber.getText().toString());
            requestUpdateWallet.setWalletAddress(edtethAddress.getText().toString());
            requestUpdateWallet.setWalletId("1");
            requestUpdateWallet.setAddressId(addressMaster.getAddressId());
            requestUpdateWallet.setWalletNickName(edtethnickName.getText().toString());
            requestUpdateWallet.setWalletPublicKey(edtethkey.getText().toString());
            RestClient.get().updateWalletApi(requestUpdateWallet).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(AddUpdateWalletDetailActivity.this, response.body().getMessage());
                            doFinish();
                        } else {
                            Functions.showToast(AddUpdateWalletDetailActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        }
    }

    private void addWalletApi() {
        if (Functions.isConnected(AddUpdateWalletDetailActivity.this)) {
            showProgressDialog(false);
            RequestAddWallet requestAddWallet = new RequestAddWallet();
            requestAddWallet.setUserId(Preferences.getInstance(AddUpdateWalletDetailActivity.this).getString(Preferences.KEY_USER_ID));
            requestAddWallet.setAdharNumber(edtAddharNumber.getText().toString());
            requestAddWallet.setWalletAddress(edtethAddress.getText().toString());
            requestAddWallet.setWalletId("1");
            requestAddWallet.setWalletNickName(edtethnickName.getText().toString());
            requestAddWallet.setWalletPublicKey(edtethkey.getText().toString());
            RestClient.get().addWalletApi(requestAddWallet).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(AddUpdateWalletDetailActivity.this, response.body().getMessage());
                            doFinish();
                        } else {
                            Functions.showToast(AddUpdateWalletDetailActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(AddUpdateWalletDetailActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        }
    }

    private void setData(AddressMaster addressMaster) {
        txtRegister.setText("Update Wallet");
        edtethAddress.setText(addressMaster.getWalletAddress());
        edtAddharNumber.setText(addressMaster.getAdharNumber());
        edtethkey.setText(addressMaster.getWalletPublicKey());
        edtethnickName.setText(addressMaster.getWalletNickName());
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtAddNewWallet = (TfTextView) toolbar.findViewById(R.id.txtAddNewWallet);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        this.scrollView = (ScrollView) findViewById(R.id.scrollView);
        this.txtRegister = (TfTextView) findViewById(R.id.txtRegister);
        this.edtAddharNumber = (MyEditTextWithCloseBtn) findViewById(R.id.edtAddharNumber);
        this.edtethkey = (MyEditTextWithCloseBtn) findViewById(R.id.edtethkey);
        this.edtethAddress = (MyEditTextWithCloseBtn) findViewById(R.id.edtethAddress);
        this.edtethnickName = (MyEditTextWithCloseBtn) findViewById(R.id.edtethnickName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                doFinish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doFinish() {
        Functions.hideKeyPad(AddUpdateWalletDetailActivity.this, scrollView);
        if (isFrom.equalsIgnoreCase("From Login")) {
            DashBoardActivity.launchDashboradActivity(AddUpdateWalletDetailActivity.this);
        } else {
            Intent intent = new Intent(AddUpdateWalletDetailActivity.this, WalletActvity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        doFinish();
    }
}
