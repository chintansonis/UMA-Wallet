package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transcation implements Serializable{

    @SerializedName("TranscationId")
    @Expose
    private String transcationId;
    @SerializedName("TokenId")
    @Expose
    private String tokenId;
    @SerializedName("TokenName")
    @Expose
    private String tokenName;
    @SerializedName("TokenShortCode")
    @Expose
    private String tokenShortCode;
    @SerializedName("TokenIconURL")
    @Expose
    private String tokenIconURL;
    @SerializedName("TokenCount")
    @Expose
    private String tokenCount;
    @SerializedName("TranscationType")
    @Expose
    private String transcationType;
    @SerializedName("FromAdd")
    @Expose
    private String fromAdd;
    @SerializedName("ToAdd")
    @Expose
    private String toAdd;
    @SerializedName("GasFee")
    @Expose
    private String gasFee;
    @SerializedName("Confirmation")
    @Expose
    private String confirmation;
    @SerializedName("Transaction")
    @Expose
    private String transaction;
    @SerializedName("TransactionTime")
    @Expose
    private String transactionTime;
    @SerializedName("Block")
    @Expose
    private String block;

    public String getTranscationId() {
        return transcationId;
    }

    public void setTranscationId(String transcationId) {
        this.transcationId = transcationId;
    }

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

    public String getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(String tokenCount) {
        this.tokenCount = tokenCount;
    }

    public String getTranscationType() {
        return transcationType;
    }

    public void setTranscationType(String transcationType) {
        this.transcationType = transcationType;
    }

    public String getFromAdd() {
        return fromAdd;
    }

    public void setFromAdd(String fromAdd) {
        this.fromAdd = fromAdd;
    }

    public String getToAdd() {
        return toAdd;
    }

    public void setToAdd(String toAdd) {
        this.toAdd = toAdd;
    }

    public String getGasFee() {
        return gasFee;
    }

    public void setGasFee(String gasFee) {
        this.gasFee = gasFee;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

}
