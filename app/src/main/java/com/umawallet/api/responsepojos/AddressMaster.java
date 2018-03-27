package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressMaster implements Serializable{

    @SerializedName("WalletId")
    @Expose
    private String walletId;
    @SerializedName("WalletAddress")
    @Expose
    private String walletAddress;
    @SerializedName("WalletPublicKey")
    @Expose
    private String walletPublicKey;
    @SerializedName("WalletNickName")
    @Expose
    private String walletNickName;
    @SerializedName("AdharNumber")
    @Expose
    private String adharNumber;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public String getWalletNickName() {
        return walletNickName;
    }

    public void setWalletNickName(String walletNickName) {
        this.walletNickName = walletNickName;
    }

    public String getAdharNumber() {
        return adharNumber;
    }

    public void setAdharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
    }

}
