package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.umawallet.R;
import com.umawallet.api.responsepojos.UserDetails;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;
import com.umawallet.ui.BaseActivity;

/**
 * Created by Shriji on 3/25/2018.
 */

public class DashBoardFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout llFab;

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
    }

    private void init(View view) {
        llFab = view.findViewById(R.id.llFab);
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
}
