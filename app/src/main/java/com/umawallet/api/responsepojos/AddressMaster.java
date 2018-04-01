package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.umawallet.helper.AppConstants;

import java.io.Serializable;
import java.util.List;

public class AddressMaster implements Serializable{
    public AddressMaster() {
    }
    public AddressMaster(String nickName) {
        addressId = AppConstants.DefaultID;
        this.walletNickName = nickName;
    }

    @SerializedName("AddressId")
    @Expose
    private String addressId;
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
    @SerializedName("Balance")
    @Expose
    private String balance;
    @SerializedName("TokenBalance")
    @Expose
    private List<TokenBalance> tokenBalance = null;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<TokenBalance> getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(List<TokenBalance> tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

}

