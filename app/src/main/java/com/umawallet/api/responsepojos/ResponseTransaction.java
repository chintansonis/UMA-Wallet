package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTransaction {

    @SerializedName("Transcation")
    @Expose
    private List<Transcation> transcation = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<Transcation> getTranscation() {
        return transcation;
    }

    public void setTranscation(List<Transcation> transcation) {
        this.transcation = transcation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
