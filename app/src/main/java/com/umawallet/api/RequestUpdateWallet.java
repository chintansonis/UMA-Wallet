package com.umawallet.api;

/**
 * Created by Shriji on 4/1/2018.
 */

public class RequestUpdateWallet {
    private String UserId;
    private String AddressId;

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    private String AdharNumber;
    private String WalletPublicKey;
    private String WalletNickName;
    private String WalletAddress;
    private String WalletId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAdharNumber() {
        return AdharNumber;
    }

    public void setAdharNumber(String adharNumber) {
        AdharNumber = adharNumber;
    }

    public String getWalletPublicKey() {
        return WalletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        WalletPublicKey = walletPublicKey;
    }

    public String getWalletNickName() {
        return WalletNickName;
    }

    public void setWalletNickName(String walletNickName) {
        WalletNickName = walletNickName;
    }

    public String getWalletAddress() {
        return WalletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        WalletAddress = walletAddress;
    }

    public String getWalletId() {
        return WalletId;
    }

    public void setWalletId(String walletId) {
        WalletId = walletId;
    }
}
