package com.umawallet.api;

/**
 * Created by Shriji on 4/1/2018.
 */

public class RequestTransfer {
   private String UserId;
   private String FullName;
   private String WalletId;
   private String TokenId;
   private String FromAdd;
   private String ToAdd;
   private String TokenName;
   private String TokenCount;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getWalletId() {
        return WalletId;
    }

    public void setWalletId(String walletId) {
        WalletId = walletId;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public String getFromAdd() {
        return FromAdd;
    }

    public void setFromAdd(String fromAdd) {
        FromAdd = fromAdd;
    }

    public String getToAdd() {
        return ToAdd;
    }

    public void setToAdd(String toAdd) {
        ToAdd = toAdd;
    }

    public String getTokenName() {
        return TokenName;
    }

    public void setTokenName(String tokenName) {
        TokenName = tokenName;
    }

    public String getTokenCount() {
        return TokenCount;
    }

    public void setTokenCount(String tokenCount) {
        TokenCount = tokenCount;
    }
}

