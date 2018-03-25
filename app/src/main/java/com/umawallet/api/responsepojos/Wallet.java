package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wallet implements Serializable{

    @SerializedName("WalletId")
    @Expose
    private String walletId;
    @SerializedName("WalletName")
    @Expose
    private String walletName;
    @SerializedName("WalletShortCode")
    @Expose
    private String walletShortCode;
    @SerializedName("WalletIconURL")
    @Expose
    private String walletIconURL;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletShortCode() {
        return walletShortCode;
    }

    public void setWalletShortCode(String walletShortCode) {
        this.walletShortCode = walletShortCode;
    }

    public String getWalletIconURL() {
        return walletIconURL;
    }

    public void setWalletIconURL(String walletIconURL) {
        this.walletIconURL = walletIconURL;
    }

}
