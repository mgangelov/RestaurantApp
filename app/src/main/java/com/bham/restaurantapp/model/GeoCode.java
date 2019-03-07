package com.bham.restaurantapp.model;

import com.google.gson.annotations.SerializedName;

public class GeoCode {
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public java.lang.String getLongitude() {
        return longitude;
    }

    public void setLongitude(java.lang.String longitude) {
        this.longitude = longitude;
    }
}
