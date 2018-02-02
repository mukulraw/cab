package com.example.faizan.voxox.NearByPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TBX on 01-02-2018.
 */

public class Estimated {

    @SerializedName("cabType")
    @Expose
    private String cabType;
    @SerializedName("typeId")
    @Expose
    private String typeId;
    @SerializedName("estimateTime")
    @Expose
    private String estimateTime;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
