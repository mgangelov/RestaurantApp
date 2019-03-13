package com.bham.restaurantapp.model.postcodes;

import com.google.gson.annotations.SerializedName;

public class Postcode {
    @SerializedName("postcode")
    private String postCode;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(this.getLongitude(), this.getLatitude());
    }
}
