package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umawallet.R;
import com.umawallet.ui.BaseActivity;

/**
 * Created by Shriji on 3/25/2018.
 */

public class StateChartFragment extends BaseFragment {
    @SuppressLint({"ValidFragment", "Unused"})
    private StateChartFragment() {
    }

    @SuppressLint("ValidFragment")
    private StateChartFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new StateChartFragment(activity);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

}
