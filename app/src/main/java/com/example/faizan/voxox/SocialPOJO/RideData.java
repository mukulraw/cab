package com.example.faizan.voxox.SocialPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faizan on 1/24/2018.
 */

public class RideData {

    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("driverId")
    @Expose
    private String driverId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
