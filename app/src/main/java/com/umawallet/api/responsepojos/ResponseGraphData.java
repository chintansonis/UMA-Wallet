package com.umawallet.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.umawallet.api.BaseResponse;

import java.util.List;

public class ResponseGraphData extends BaseResponse {
    @SerializedName("GraphData")
    @Expose
    private List<GraphDatum> graphData = null;

    public List<GraphDatum> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphDatum> graphData) {
        this.graphData = graphData;
    }
}