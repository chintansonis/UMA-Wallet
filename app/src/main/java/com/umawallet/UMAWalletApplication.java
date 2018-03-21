package com.umawallet;

import android.app.Application;

import com.umawallet.api.RestClient;

/**
 * Created by chintans on 21-03-2018.
 */

public class UMAWalletApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        RestClient.setupRestClient();
    }

}
