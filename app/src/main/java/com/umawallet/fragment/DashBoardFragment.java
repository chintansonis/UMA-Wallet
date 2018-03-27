package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.umawallet.R;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.api.responsepojos.UserDetails;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;
import com.umawallet.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 3/25/2018.
 */

public class DashBoardFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout llFab;
    private android.support.v4.view.ViewPager pagerTabs;
    private android.support.design.widget.TabLayout tabs;
    private ArrayList<AddressMaster> addressMasterArrayList = new ArrayList<>();


    @SuppressLint({"ValidFragment", "Unused"})
    private DashBoardFragment() {
    }

    @SuppressLint("ValidFragment")
    private DashBoardFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new DashBoardFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
        userDetails = new Gson().fromJson(Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_USER_MODEL), UserDetails.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init(view);
        if (Functions.isConnected(getBaseActivity())) {
            callGetUserAddressApi();
        } else {
            Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void callGetUserAddressApi() {
        getBaseActivity().showProgressDialog(false);
        RestClient.get().getUserAddressApi(Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_USER_ID)).enqueue(new Callback<ResponseGeUserAddress>() {
            @Override
            public void onResponse(Call<ResponseGeUserAddress> call, Response<ResponseGeUserAddress> response) {
                getBaseActivity().hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        addressMasterArrayList.addAll(response.body().getAddressMaster());
                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
                        for (int i = 0; i < addressMasterArrayList.size(); i++) {
                            viewPagerAdapter.addFragment(new TabTransactionFragment(addressMasterArrayList.get(i).getWalletAddress()), addressMasterArrayList.get(i).getWalletNickName());
                        }
                        pagerTabs.setAdapter(viewPagerAdapter);
                        tabs.setupWithViewPager(pagerTabs);
                    } else {
                        Functions.showToast(getBaseActivity(), response.body().getMessage());
                    }
                } else {
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ResponseGeUserAddress> call, Throwable t) {
                getBaseActivity().hideProgressDialog();
                Functions.showToast(getBaseActivity(), t.getMessage());
            }
        });
    }


    private void init(View view) {
        llFab = view.findViewById(R.id.llFab);
        this.tabs = (android.support.design.widget.TabLayout) view.findViewById(R.id.tabs);
        pagerTabs = view.findViewById(R.id.pagerTabs);
        llFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.showToast(getBaseActivity(), getBaseActivity().getString(R.string.this_feature_under_dev));
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

    }
}
