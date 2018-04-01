package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.umawallet.api.BaseResponse;

import java.util.List;


public class ResponseGetPrice extends BaseResponse {
    @SerializedName("PriceData")
    @Expose
    private List<PriceDatum> priceData = null;

    public List<PriceDatum> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<PriceDatum> priceData) {
        this.priceData = priceData;
    }

}