package com.bham.restaurantapp.model.postcodes;

import com.google.gson.annotations.SerializedName;

public class PostcodeResult {
    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private Postcode result;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Postcode getResult() {
        return result;
    }

    public void setResult(Postcode result) {
        this.result = result;
    }
}
