package com.umawallet.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;

import com.umawallet.R;
import com.umawallet.custom.TfTextView;
import com.umawallet.fragment.BaseFragment;
import com.umawallet.fragment.DashBoardFragment;
import com.umawallet.fragment.SettingFragment;
import com.umawallet.fragment.StateChartFragment;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;

import java.util.Stack;


/**
 * Created by chintans on 15-02-2018.
 */

public class BaseActivity extends AppCompatActivity {
    private static Context context;
    private Stack<Fragment> fragmentBackStack;
    public int screenWidth, screenHeight;
    private boolean showBackMessage = true;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce;
    private ProgressDialog dialog;
    public static int CALL_PERMISSION_REQUEST_CODE = 1225;
    public static int CONTACT_PERMISSION_REQUEST_CODE = 1220;
    public String isFrom = "";
    public static String productId = "";


    /**
     * Instance of current logined user.
     */
    /*protected UserDetails userDetails;*/

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setShowBackMessage(boolean showBackMessage) {
        this.showBackMessage = showBackMessage;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


    public Stack<Fragment> getFragments() {
        return fragmentBackStack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        fragmentBackStack = new Stack<>();
        getWidthAndHeight();
    }

    public void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    /**
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to show, in tab identified by tag shouldAnimate ->
     * should animate transaction. false when we switch tabs, or adding first fragment to a tab true when when we are pushing more
     * fragment into navigation stack. shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)). false when we are
     * switching tabs (except for the first time) true in all other cases.
     *
     * @param fragment      the fragment
     * @param shouldAnimate the should animate
     * @param shouldAdd     the should add
     */
    public synchronized void pushAddFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
        if (fragment != null) {
            fragmentBackStack.push(fragment);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            //Fragment currentFragmentForAdd = fragmentBackStack.get(fragmentBackStack.size() - 1);
            ft.replace(R.id.contentFrame, fragment, String.valueOf(fragmentBackStack.size()));
            ft.commit();
            manager.executePendingTransactions();
            if (drawerLayout != null && fragmentBackStack.size() > 1) {
                Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                /*if (currentFragment instanceof DashBoardFragment) {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                   } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }*/
            }
        }
    }

    /**
     * Select the second last fragment in current tab's stack.. which will be shown after the fragment transaction given below
     *
     * @param shouldAnimate the should animate
     */
    public synchronized void popFragments(boolean shouldAnimate) {
        Fragment fragment = null;
        if (fragmentBackStack.size() > 1) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 2);
        } else if (!fragmentBackStack.isEmpty()) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 1);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                ft.remove(fragmentBackStack.elementAt(fragmentBackStack.size() - 1));
                if (fragmentBackStack.size() > 1) {
                    ft.show(fragment).commit();
                }
            } else {
                ft.replace(R.id.contentFrame, fragment).commit();
            }
            fragmentBackStack.pop();
            manager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (fragmentBackStack.size() <= 1) {
            if (showBackMessage) {
                doubleTapOnBackPress();
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        } else {
            if (!((BaseFragment) fragmentBackStack.get(fragmentBackStack.size() - 1)).onFragmentBackPress()) {
                Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                if (currentFragment instanceof SettingFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof StateChartFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof DashBoardFragment) {
                    doubleTapOnBackPress();
                } else {
                    popFragments(true);
                }
            }
        }
    }

    private void loadHomeFragment() {
        setHeaderTitle(getString(R.string.app_name));
        getFragments().clear();
        Fragment fragmentToPush = DashBoardFragment.getFragment(this);
        pushAddFragments(fragmentToPush, true, true);
        loadBottomUI(AppConstants.FOOTER_HOME);
    }

    /**
     * method handle for show notification for close the application & Stop app to Close when there is no any remaining Fragment
     * in Stack and User press Back Press
     */
    void doubleTapOnBackPress() {
        if (doubleBackToExitPressedOnce) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Functions.showToast(this, context.getString(R.string.click_back_to_exit));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }

    public void showProgressDialog(boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage(context.getString(R.string.msg_please_wait));
        dialog.setCancelable(isCancelable);
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * this method load dashboard fragment
     */
    /*public void loadHomeFragment() {
        ((TfTextView) findViewById(R.id.txtCurrentLocation)).setText(Functions.getLocationOfaddressHeader(context, Preferences.getInstance(context).getString(Preferences.KEY_LATITUDE),Preferences.getInstance(context).getString(Preferences.KEY_LONGITUDE)));
        isVisibleLocation(true);
        isVisibleChangePassword(false);
        isVisibleSearch(true);
        isVisibleHeaderImage(false);
        setHeaderFRomBaseActivity(getString(R.string.app_name));
        getFragments().clear();
        Fragment fragmentToPush = DashBoardFragment.getFragment(this);
        pushAddFragments(fragmentToPush, true, true);
    }*/
    public void loadBottomUI(int selectedPosition) {
        switch (selectedPosition) {
            case 1:
                ((ImageView) findViewById(R.id.ivFooterHome)).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                ((ImageView) findViewById(R.id.ivFooterSettings)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((ImageView) findViewById(R.id.ivFooterStates)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterHome)).setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                ((TfTextView) findViewById(R.id.txtFooterSettings)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterState)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                break;
            case 2:
                ((ImageView) findViewById(R.id.ivFooterHome)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((ImageView) findViewById(R.id.ivFooterSettings)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((ImageView) findViewById(R.id.ivFooterStates)).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                ((TfTextView) findViewById(R.id.txtFooterHome)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterSettings)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterState)).setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                break;
            case 3:
                ((ImageView) findViewById(R.id.ivFooterHome)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((ImageView) findViewById(R.id.ivFooterSettings)).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                ((ImageView) findViewById(R.id.ivFooterStates)).setColorFilter(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterHome)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                ((TfTextView) findViewById(R.id.txtFooterSettings)).setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                ((TfTextView) findViewById(R.id.txtFooterState)).setTextColor(ContextCompat.getColor(context, R.color.bottom_unselect));
                break;
        }
    }
    public void setHeaderTitle(String title) {
        ((TfTextView) findViewById(R.id.txtTitle)).setText(title);
        ((TfTextView) findViewById(R.id.txtTitle)).setTextColor(getResources().getColor(R.color.white));
    }
}
