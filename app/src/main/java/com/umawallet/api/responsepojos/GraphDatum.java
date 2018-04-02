package com.umawallet.api.responsepojos;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GraphDatum implements Serializable,Comparable {
    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Price")
    @Expose
    private float price;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
