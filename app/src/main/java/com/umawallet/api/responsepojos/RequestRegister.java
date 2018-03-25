package com.umawallet.api.responsepojos;

/**
 * Created by Shriji on 3/24/2018.
 */

public class RequestRegister {
    private String Email;
    private String FullName;
    private String Password;
    private String AdharNumber;
    private String WalletPublicKey;
    private String WalletNickName;
    private String WalletAddress;
    private String WalletId;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
