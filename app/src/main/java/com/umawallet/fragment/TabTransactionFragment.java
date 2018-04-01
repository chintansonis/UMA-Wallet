package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umawallet.R;
import com.umawallet.adapter.TransactionAdpater;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.RequestFromAddress;
import com.umawallet.api.responsepojos.ResponseGetPrice;
import com.umawallet.api.responsepojos.ResponseTransaction;
import com.umawallet.api.responsepojos.Transcation;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anish on 19-01-2018.
 */

@SuppressLint("ValidFragment")
public class TabTransactionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private final String wallet_address;
    private ProgressDialog dialog;
    private android.support.v7.widget.RecyclerView rvRigDetail;
    private LinearLayout linearTokens;
    private LinearLayout llTokens;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Transcation> transcationArrayList = new ArrayList<>();
    private TfTextView txtNoDataFound;
    private TransactionAdpater transactionAdpater;
    private TfTextView tvPrice;
    private String price = "";


    @SuppressLint("ValidFragment")
    public TabTransactionFragment(String account_owner_name, String price) {
        this.wallet_address = account_owner_name;
        this.price = price;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_transaction_tab_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRigDetail = (RecyclerView) view.findViewById(R.id.rvRigDetail);
        linearTokens = (LinearLayout) view.findViewById(R.id.linearTokens);
        llTokens = (LinearLayout) view.findViewById(R.id.llTokens);
        llTokens.setVisibility(View.VISIBLE);
        txtNoDataFound = (TfTextView) view.findViewById(R.id.txtNoDataFound);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvPrice.setVisibility(View.VISIBLE);
        if (Preferences.getInstance(getActivity()).getString(Preferences.KEY_CURRENCY).equalsIgnoreCase(AppConstants.CURRENCY_INR)) {
            tvPrice.setText(getActivity().getString(R.string.rs_symbol) + " " + price);
        } else {
            tvPrice.setText(getActivity().getString(R.string.dolor_symbol) + " " + price);
        }
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getTranscationApi();
        // initAdapter();
    }

    private void getTranscationApi() {
        if (Functions.isConnected(getActivity())) {
            RequestFromAddress requestFromAddress = new RequestFromAddress();
            showProgressDialog(false);
            requestFromAddress.setFromAddress(wallet_address);
            RestClient.get().getTranscationApi(requestFromAddress).enqueue(new Callback<ResponseTransaction>() {
                @Override
                public void onResponse(Call<ResponseTransaction> call, Response<ResponseTransaction> response) {
                    hideProgressDialog();
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (Functions.isConnected(getActivity())) {
                        callgetPriceApi();
                    } else {
                        Functions.showToast(getActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            if (transcationArrayList != null) {
                                transcationArrayList.clear();
                            }
                            transcationArrayList.addAll(response.body().getTranscation());
                            checkVisibility();
                            initAdapter();
                        } else {
                            checkVisibility();
                        }
                    } else {
                        Functions.showToast(getActivity(), getString(R.string.err_something_went_wrong));
                        checkVisibility();
                    }
                }

                @Override
                public void onFailure(Call<ResponseTransaction> call, Throwable t) {
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    hideProgressDialog();
                    checkVisibility();
                    Functions.showToast(getActivity(), getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
            checkVisibility();
            Functions.showToast(getActivity(), getString(R.string.err_no_internet_connection));
        }
    }

    private void callgetPriceApi() {
        showProgressDialog(false);
        RestClient.get().getPriceApi(Preferences.getInstance(getActivity()).getString(Preferences.KEY_CURRENCY)).enqueue(new Callback<ResponseGetPrice>() {
            @Override
            public void onResponse(Call<ResponseGetPrice> call, Response<ResponseGetPrice> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if (linearTokens != null) {
                            linearTokens.removeAllViews();
                        }
                        for (int i = 0; i < response.body().getPriceData().size(); i++) {
                            View cardview = getActivity().getLayoutInflater().inflate(R.layout.item_price, linearTokens, false);
                            ImageView imgTransaction = cardview.findViewById(R.id.imgTransaction);
                            TextView tvWalletTransferToken = cardview.findViewById(R.id.tvWalletTransferToken);
                            TextView txtTranscationAmount = cardview.findViewById(R.id.txtTranscationAmount);
                            if (Preferences.getInstance(getActivity()).getString(Preferences.KEY_CURRENCY).equalsIgnoreCase(AppConstants.CURRENCY_INR)) {
                                txtTranscationAmount.setText(getString(R.string.rs_symbol) + " " + response.body().getPriceData().get(i).getLastPrice() + "");
                            } else {
                                txtTranscationAmount.setText(getString(R.string.dolor_symbol) + " " + response.body().getPriceData().get(i).getLastPrice() + "");
                            }
                            tvWalletTransferToken.setText(response.body().getPriceData().get(i).getTokenName() + "  (" + response.body().getPriceData().get(i).getTokenShortCode() + " )");
                            Functions.loadImageFromURL(getActivity(), response.body().getPriceData().get(i).getTokenIconURL(), imgTransaction, new ProgressBar(getActivity()));
                            linearTokens.addView(cardview);
                        }
                    } else {
                        Functions.showToast(getActivity(), response.body().getMessage());
                    }
                } else {
                    checkVisibility();
                    Functions.showToast(getActivity(), getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ResponseGetPrice> call, Throwable t) {
                checkVisibility();
                hideProgressDialog();
                Functions.showToast(getActivity(), t.getMessage());
            }
        });

    }

    private void initAdapter() {
        rvRigDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionAdpater = new TransactionAdpater(getActivity(), transcationArrayList);
        rvRigDetail.setAdapter(transactionAdpater);
    }

    private void checkVisibility() {
        if (transcationArrayList.size() > 0) {
            swipeContainer.setVisibility(View.VISIBLE);
            txtNoDataFound.setVisibility(View.GONE);
        } else {
            swipeContainer.setVisibility(View.GONE);
            txtNoDataFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        getTranscationApi();
    }

    public void showProgressDialog(boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.msg_please_wait));
        dialog.setCancelable(isCancelable);
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
