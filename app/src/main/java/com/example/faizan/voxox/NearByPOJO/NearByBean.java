package com.example.faizan.voxox.NearByPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faizan on 1/29/2018.
 */

public class NearByBean {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("estimated")
    @Expose
    private List<Estimated> estimated = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Estimated> getEstimated() {
        return estimated;
    }

    public void setEstimated(List<Estimated> estimated) {
        this.estimated = estimated;
    }
}
