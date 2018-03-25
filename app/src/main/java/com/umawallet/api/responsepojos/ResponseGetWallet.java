package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.umawallet.api.BaseResponse;

import java.util.List;

public class ResponseGetWallet extends BaseResponse{

    @SerializedName("Wallet")
    @Expose
    private List<Wallet> wallet = null;

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }


}

