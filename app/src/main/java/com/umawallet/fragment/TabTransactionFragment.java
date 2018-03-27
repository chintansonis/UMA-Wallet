package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umawallet.R;
import com.umawallet.adapter.TransactionAdpater;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.RequestFromAddress;
import com.umawallet.api.responsepojos.ResponseTransaction;
import com.umawallet.api.responsepojos.Transcation;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;

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
    private android.support.v7.widget.RecyclerView rvRigDetail;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Transcation> transcationArrayList = new ArrayList<>();
    private TfTextView txtNoDataFound;
    private TransactionAdpater transactionAdpater;


    @SuppressLint("ValidFragment")
    public TabTransactionFragment(String account_owner_name) {
        this.wallet_address = account_owner_name;
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
        txtNoDataFound = (TfTextView) view.findViewById(R.id.txtNoDataFound);
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
            requestFromAddress.setFromAddress(wallet_address);
            RestClient.get().getTranscationApi(requestFromAddress).enqueue(new Callback<ResponseTransaction>() {
                @Override
                public void onResponse(Call<ResponseTransaction> call, Response<ResponseTransaction> response) {
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            if(transcationArrayList!=null){
                                transcationArrayList.clear();
                            }
                            transcationArrayList.addAll(response.body().getTranscation());
                            checkVisibility();
                            initAdapter();
                        } else {
                            checkVisibility();
                            Functions.showToast(getActivity(), response.body().getMessage());
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

    private void initAdapter() {
        rvRigDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionAdpater = new TransactionAdpater(getActivity(),transcationArrayList);
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


}
