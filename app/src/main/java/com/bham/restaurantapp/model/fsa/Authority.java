package com.bham.restaurantapp.model.fsa;

import com.google.gson.annotations.SerializedName;

public class Authority {
    @SerializedName("Name")
    private String name;
    @SerializedName("LocalAuthorityId")
    private int id;
    @SerializedName("RegionName")
    private String regionName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
