package com.example.faizan.voxox.EstimatedFarePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faizan on 1/30/2018.
 */

public class Data {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("pickUpLat")
    @Expose
    private String pickUpLat;
    @SerializedName("pickUpLong")
    @Expose
    private String pickUpLong;
    @SerializedName("dropLat")
    @Expose
    private String dropLat;
    @SerializedName("dropLong")
    @Expose
    private String dropLong;
    @SerializedName("cabType")
    @Expose
    private String cabType;
    @SerializedName("cabName")
    @Expose
    private String cabName;
    @SerializedName("cabNumber")
    @Expose
    private String cabNumber;
    @SerializedName("apiURL")
    @Expose
    private String apiURL;
    @SerializedName("apiPosition")
    @Expose
    private String apiPosition;
    @SerializedName("driverArrivalTime")
    @Expose
    private String driverArrivalTime;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("driverLat")
    @Expose
    private String driverLat;
    @SerializedName("driverLong")
    @Expose
    private String driverLong;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverImage")
    @Expose
    private String driverImage;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("driverRating")
    @Expose
    private String driverRating;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("estimated")
    @Expose
    private List<Estimated> estimated = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpLat = pickUpLat;
    }

    public String getPickUpLong() {
        return pickUpLong;
    }

    public void setPickUpLong(String pickUpLong) {
        this.pickUpLong = pickUpLong;
    }

    public String getDropLat() {
        return dropLat;
    }

    public void setDropLat(String dropLat) {
        this.dropLat = dropLat;
    }

    public String getDropLong() {
        return dropLong;
    }

    public void setDropLong(String dropLong) {
        this.dropLong = dropLong;
    }

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public String getCabName() {
        return cabName;
    }

    public void setCabName(String cabName) {
        this.cabName = cabName;
    }

    public String getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public String getApiPosition() {
        return apiPosition;
    }

    public void setApiPosition(String apiPosition) {
        this.apiPosition = apiPosition;
    }

    public String getDriverArrivalTime() {
        return driverArrivalTime;
    }

    public void setDriverArrivalTime(String driverArrivalTime) {
        this.driverArrivalTime = driverArrivalTime;
    }

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

    public String getDriverLat() {
        return driverLat;
    }

    public void setDriverLat(String driverLat) {
        this.driverLat = driverLat;
    }

    public String getDriverLong() {
        return driverLong;
    }

    public void setDriverLong(String driverLong) {
        this.driverLong = driverLong;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public List<Estimated> getEstimated() {
        return estimated;
    }

    public void setEstimated(List<Estimated> estimated) {
        this.estimated = estimated;
    }
}
