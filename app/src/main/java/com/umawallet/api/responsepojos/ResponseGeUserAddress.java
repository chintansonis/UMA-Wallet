package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGeUserAddress {
    @SerializedName("AddressMaster")
    @Expose
    private List<AddressMaster> addressMaster = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<AddressMaster> getAddressMaster() {
        return addressMaster;
    }

    public void setAddressMaster(List<AddressMaster> addressMaster) {
        this.addressMaster = addressMaster;
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