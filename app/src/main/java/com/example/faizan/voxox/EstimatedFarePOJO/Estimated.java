package com.example.faizan.voxox.EstimatedFarePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faizan on 1/30/2018.
 */

public class Estimated {
    @SerializedName("cabType")
    @Expose
    private String cabType;
    @SerializedName("typeId")
    @Expose
    private String typeId;
    @SerializedName("price")
    @Expose
    private Double price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }}
