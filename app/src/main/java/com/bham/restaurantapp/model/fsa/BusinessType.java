package com.bham.restaurantapp.model.fsa;

import com.google.gson.annotations.SerializedName;

public class BusinessType {
    @SerializedName("BusinessTypeId")
    private int bTypeId;
    @SerializedName("BusinessTypeName")
    private String bTypeName;

    public int getbTypeId() {
        return bTypeId;
    }

    public void setbTypeId(int bTypeId) {
        this.bTypeId = bTypeId;
    }

    public String getbTypeName() {
        return bTypeName;
    }

    public void setbTypeName(String bTypeName) {
        this.bTypeName = bTypeName;
    }
}
