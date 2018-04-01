package com.umawallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.umawallet.R;
import com.umawallet.adapter.WalletListAdpater;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 4/1/2018.
 */

public class WalletActvity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static ArrayList<AddressMaster> addressMasterArrayList = new ArrayList<>();
    private android.support.v7.widget.RecyclerView rvRigDetail;
    public static android.support.v4.widget.SwipeRefreshLayout swipeContainer;
    public static com.umawallet.custom.TfTextView txtNoDataFound;
    private LinearLayout llAppBar;
    private WalletListAdpater walletListAdpater;
    private android.support.v7.widget.Toolbar toolbar;
    private TfTextView txtTitle,txtAddNewWallet;

    public static void launchWalletActivity(BaseActivity activity) {
        if (activity != null) {
            Functions.fireIntent(activity, WalletActvity.class);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transaction_tab_detail);
        setShowBackMessage(false);
        init();
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtAddNewWallet = (TfTextView) toolbar.findViewById(R.id.txtAddNewWallet);
        toolbar.setTitle("");
        txtAddNewWallet.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(getString(R.string.wallet));
        txtAddNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActvity.this, AddUpdateWalletDetailActivity.class);
                intent.putExtra("isFrom","Add Wallet");
                Functions.fireIntent(WalletActvity.this, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    private void init() {
        this.txtNoDataFound = (TfTextView) findViewById(R.id.txtNoDataFound);
        this.llAppBar = (LinearLayout) findViewById(R.id.llAppBar);
        llAppBar.setVisibility(View.VISIBLE);
        this.swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        this.rvRigDetail = (RecyclerView) findViewById(R.id.rvRigDetail);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if (Functions.isConnected(WalletActvity.this)) {
            callGetUserAddressApi();
        } else {
            checkVisibility(addressMasterArrayList);
            Functions.showToast(WalletActvity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void callGetUserAddressApi() {
        showProgressDialog(false);
        RestClient.get().getUserAddressApi(Preferences.getInstance(WalletActvity.this).getString(Preferences.KEY_USER_ID), Preferences.getInstance(WalletActvity.this).getString(Preferences.KEY_CURRENCY)).enqueue(new Callback<ResponseGeUserAddress>() {
            @Override
            public void onResponse(Call<ResponseGeUserAddress> call, Response<ResponseGeUserAddress> response) {
                hideProgressDialog();
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if(addressMasterArrayList!=null){
                            addressMasterArrayList.clear();
                        }
                        addressMasterArrayList.addAll(response.body().getAddressMaster());
                        initAdapter();
                        checkVisibility(addressMasterArrayList);
                    } else {
                        checkVisibility(addressMasterArrayList);
                    }
                } else {
                    checkVisibility(addressMasterArrayList);
                    Functions.showToast(WalletActvity.this, getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ResponseGeUserAddress> call, Throwable t) {
                hideProgressDialog();
                checkVisibility(addressMasterArrayList);
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                Functions.showToast(WalletActvity.this, t.getMessage());
            }
        });
    }

    private void initAdapter() {
        rvRigDetail.setLayoutManager(new LinearLayoutManager(WalletActvity.this));
        walletListAdpater = new WalletListAdpater(WalletActvity.this, addressMasterArrayList);
        rvRigDetail.setAdapter(walletListAdpater);
    }

    public static void checkVisibility(ArrayList<AddressMaster> addressMasterArrayList) {
        if (addressMasterArrayList.size() > 0) {
            swipeContainer.setVisibility(View.VISIBLE);
            txtNoDataFound.setVisibility(View.GONE);
        } else {
            swipeContainer.setVisibility(View.GONE);
            txtNoDataFound.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        if (Functions.isConnected(WalletActvity.this)) {
            callGetUserAddressApi();
        } else {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
            Functions.showToast(WalletActvity.this, getResources().getString(R.string.err_no_internet_connection));
        }
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
