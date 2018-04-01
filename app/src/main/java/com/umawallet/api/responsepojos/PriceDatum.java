package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceDatum implements Serializable {

    @SerializedName("TokenId")
    @Expose
    private String tokenId;
    @SerializedName("WalletId")
    @Expose
    private String walletId;
    @SerializedName("TokenName")
    @Expose
    private String tokenName;
    @SerializedName("TokenShortCode")
    @Expose
    private String tokenShortCode;
    @SerializedName("TokenIconURL")
    @Expose
    private String tokenIconURL;
    @SerializedName("LastPrice")
    @Expose
    private String lastPrice;
    @SerializedName("GainLoss_Percentage")
    @Expose
    private String gainLossPercentage;
    @SerializedName("Volume")
    @Expose
    private String volume;
    @SerializedName("MarketCap_USD_BILLIONS")
    @Expose
    private String marketCapUSDBILLIONS;
    @SerializedName("Market_Cap_INR_Crores")
    @Expose
    private String marketCapINRCrores;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenShortCode() {
        return tokenShortCode;
    }

    public void setTokenShortCode(String tokenShortCode) {
        this.tokenShortCode = tokenShortCode;
    }

    public String getTokenIconURL() {
        return tokenIconURL;
    }

    public void setTokenIconURL(String tokenIconURL) {
        this.tokenIconURL = tokenIconURL;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getGainLossPercentage() {
        return gainLossPercentage;
    }

    public void setGainLossPercentage(String gainLossPercentage) {
        this.gainLossPercentage = gainLossPercentage;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMarketCapUSDBILLIONS() {
        return marketCapUSDBILLIONS;
    }

    public void setMarketCapUSDBILLIONS(String marketCapUSDBILLIONS) {
        this.marketCapUSDBILLIONS = marketCapUSDBILLIONS;
    }

    public String getMarketCapINRCrores() {
        return marketCapINRCrores;
    }

    public void setMarketCapINRCrores(String marketCapINRCrores) {
        this.marketCapINRCrores = marketCapINRCrores;
    }

}
