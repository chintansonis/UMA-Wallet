package com.umawallet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.umawallet.ui.BaseActivity;


/**
 * Created by chintans on 15-02-2018.
 */

public class BaseFragment extends Fragment {
    /*protected UserDetails userDetails;*/
    protected View view;
    /**
     * The Activity.
     */
    private BaseActivity baseActivity;
    /**
     * Gets base activity.
     *
     * @return the base activity
     */
    public BaseActivity getBaseActivity()
    {
        return baseActivity;
    }
    /**
     * Sets base activity.
     *
     * @param baseActivity the base activity
     */
    public void setBaseActivity(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }
    /**
     * On fragment back press boolean.
     *
     * @return the boolean
     */
    public boolean onFragmentBackPress()
    {
        return false;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
}
