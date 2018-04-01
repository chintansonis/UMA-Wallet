package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.umawallet.helper.AppConstants;

import java.io.Serializable;

public class TokenBalance implements Serializable{
    public TokenBalance() {
    }
    public TokenBalance(String TokenName) {
        tokenId = AppConstants.DefaultID;
        this.tokenName = TokenName;
    }

    @SerializedName("TokenId")
    @Expose
    private String tokenId;
    @SerializedName("TokenName")
    @Expose
    private String tokenName;
    @SerializedName("TokenCount")
    @Expose
    private String tokenCount;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(String tokenCount) {
        this.tokenCount = tokenCount;
    }

}
