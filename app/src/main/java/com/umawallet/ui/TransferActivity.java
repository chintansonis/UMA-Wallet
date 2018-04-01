package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.umawallet.R;
import com.umawallet.adapter.MySpinnerAdapter;
import com.umawallet.api.BaseResponse;
import com.umawallet.api.RequestTransfer;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.api.responsepojos.TokenBalance;
import com.umawallet.api.responsepojos.UserDetails;
import com.umawallet.custom.MyEditTextWithCloseBtn;
import com.umawallet.custom.TfTextView;
import com.umawallet.custom.customspinner.SearchableSpinner;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 4/1/2018.
 */

public class TransferActivity extends BaseActivity {
    private List<AddressMaster> addressMasters;
    private List<TokenBalance> tokenBalances;
    private MySpinnerAdapter<AddressMaster> addressMasterMySpinnerAdapter;
    private MySpinnerAdapter<TokenBalance> tokenBalanceMySpinnerAdapter;
    private com.umawallet.custom.customspinner.SearchableSpinner walletSpinner;
    private com.umawallet.custom.customspinner.SearchableSpinner tokenSpinner;
    private com.umawallet.custom.TfTextView txtAvailableToken;
    private com.umawallet.custom.TfTextView txtYourAddress;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtToAddress;
    private com.umawallet.custom.MyEditTextWithCloseBtn edtTokens;
    private com.umawallet.custom.TfTextView txtLogin;
    private android.widget.ScrollView scrollView;
    private android.support.v7.widget.Toolbar toolbar;
    private TfTextView txtTitle;
    private AddressMaster addressMaster;
    private int selectedAddressID = 0;
    private boolean mIsStateSpinnerFirstCall = true, mIsTokneSpinnerFirstCall = true;
    private TokenBalance selectedtoken;
    private int selectedTokenID = 0;
    UserDetails userDetails;
    private LinearLayout llTokensAvailable;

    public static void launchTransferActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, TransferActivity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_transfer);
        setShowBackMessage(false);
        userDetails = new Gson().fromJson(Preferences.getInstance(TransferActivity.this).getString(Preferences.KEY_USER_MODEL), UserDetails.class);
        init();
        initToolbar();
        actionListener();
        if (Functions.isConnected(TransferActivity.this)) {
            callGetUserAddressApi();
        } else {
            Functions.showToast(TransferActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void actionListener() {
        walletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mIsStateSpinnerFirstCall) {
                    addressMaster = (AddressMaster) parent.getSelectedItem();
                    selectedAddressID = Integer.valueOf(addressMaster.getAddressId());
                    if (selectedAddressID != 0) {
                        if (tokenBalances != null) {
                            tokenBalances.clear();
                        }
                        if (tokenBalanceMySpinnerAdapter != null) {
                            tokenBalanceMySpinnerAdapter.clear();
                        }
                        tokenBalances.add(new TokenBalance(getResources().getString(R.string.select_token)));
                        tokenBalances.addAll(addressMaster.getTokenBalance());
                        tokenBalanceMySpinnerAdapter.addAll(tokenBalances);
                        txtYourAddress.setText(addressMaster.getWalletAddress());
                    }
                }
                mIsStateSpinnerFirstCall = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tokenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mIsTokneSpinnerFirstCall) {
                    selectedtoken = (TokenBalance) parent.getSelectedItem();
                    selectedTokenID = Integer.valueOf(selectedtoken.getTokenId());
                    llTokensAvailable.setVisibility(View.VISIBLE);
                    txtAvailableToken.setText("Available token " + selectedtoken.getTokenCount() + "");
                }
                mIsTokneSpinnerFirstCall = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAddressID == 0) {
                    Functions.showToast(TransferActivity.this, "Please select wallet nick name");
                } else if (selectedTokenID == 0) {
                    Functions.showToast(TransferActivity.this, "Please select atleast one token");
                } else if (TextUtils.isEmpty(edtToAddress.getText().toString().trim())) {
                    Functions.showToast(TransferActivity.this, "Please enter To Address whom you want to transfer.");
                } else if (TextUtils.isEmpty(edtTokens.getText().toString().trim())) {
                    Functions.showToast(TransferActivity.this, "Please enter number of tokens.");
                } else if (Integer.parseInt(edtTokens.getText().toString().trim()) < 1) {
                    Functions.showToast(TransferActivity.this, "Please enter valid number of tokens.");
                } else if (Integer.parseInt(edtTokens.getText().toString().trim()) > Integer.parseInt(selectedtoken.getTokenCount())) {
                    Functions.showToast(TransferActivity.this, "Insufficient number of tokens.");
                } else {
                    trasFerDataApi();
                }
            }
        });
    }

    private void trasFerDataApi() {
        if (Functions.isConnected(TransferActivity.this)) {
            showProgressDialog(false);
            RequestTransfer requestTransfer = new RequestTransfer();
            requestTransfer.setFromAdd(txtYourAddress.getText().toString().trim());
            requestTransfer.setToAdd(edtToAddress.getText().toString().trim());
            requestTransfer.setFullName(userDetails.getFullName());
            requestTransfer.setTokenCount(edtTokens.getText().toString().trim());
            requestTransfer.setTokenId(String.valueOf(selectedTokenID));
            requestTransfer.setTokenName(selectedtoken.getTokenName());
            requestTransfer.setWalletId("1");
            requestTransfer.setUserId(Preferences.getInstance(TransferActivity.this).getString(Preferences.KEY_USER_ID));
            RestClient.get().transferApi(requestTransfer).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(TransferActivity.this, response.body().getMessage());
                            doFinish();
                        } else {
                            Functions.showToast(TransferActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(TransferActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(TransferActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(getString(R.string.token_transfer));
    }

    private void callGetUserAddressApi() {
        showProgressDialog(false);
        RestClient.get().getUserAddressApi(Preferences.getInstance(TransferActivity.this).getString(Preferences.KEY_USER_ID), Preferences.getInstance(TransferActivity.this).getString(Preferences.KEY_CURRENCY)).enqueue(new Callback<ResponseGeUserAddress>() {
            @Override
            public void onResponse(Call<ResponseGeUserAddress> call, Response<ResponseGeUserAddress> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        addressMasters.add(new AddressMaster(getResources().getString(R.string.select_nick_name)));
                        addressMasters.addAll(response.body().getAddressMaster());
                        addressMasterMySpinnerAdapter.addAll(addressMasters);
                    } else {
                        Functions.showToast(TransferActivity.this, response.body().getMessage());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseGeUserAddress> call, Throwable t) {
                hideProgressDialog();
                Functions.showToast(TransferActivity.this, getString(R.string.err_something_went_wrong));
            }
        });
    }

    private void init() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        txtLogin = (TfTextView) findViewById(R.id.txtLogin);
        edtTokens = (MyEditTextWithCloseBtn) findViewById(R.id.edtTokens);
        edtToAddress = (MyEditTextWithCloseBtn) findViewById(R.id.edtToAddress);
        llTokensAvailable = (LinearLayout) findViewById(R.id.llTokensAvailable);
        txtYourAddress = (TfTextView) findViewById(R.id.txtYourAddress);
        txtAvailableToken = (TfTextView) findViewById(R.id.txtAvailableToken);
        tokenSpinner = (SearchableSpinner) findViewById(R.id.tokenSpinner);
        walletSpinner = (SearchableSpinner) findViewById(R.id.walletSpinner);
        addressMasters = new ArrayList<>();
        tokenBalances = new ArrayList<>();
        addressMasterMySpinnerAdapter = new MySpinnerAdapter<AddressMaster>(this);
        tokenBalanceMySpinnerAdapter = new MySpinnerAdapter<TokenBalance>(this);
        walletSpinner.setAdapter(addressMasterMySpinnerAdapter);
        tokenSpinner.setAdapter(tokenBalanceMySpinnerAdapter);
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
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        doFinish();
    }
}
