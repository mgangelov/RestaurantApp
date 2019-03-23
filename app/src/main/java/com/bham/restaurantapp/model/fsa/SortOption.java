package com.bham.restaurantapp.model.fsa;

import com.google.gson.annotations.SerializedName;

public class SortOption {
    @SerializedName("sortOptionId")
    private int sortOptionId;
    @SerializedName("sortOptionName")
    private String sortOptionName;
    @SerializedName("sortOptionKey")
    private String sortOptionKey;

    public int getSortOptionId() {
        return sortOptionId;
    }

    public void setSortOptionId(int sortOptionId) {
        this.sortOptionId = sortOptionId;
    }

    public String getSortOptionName() {
        return sortOptionName;
    }

    public void setSortOptionName(String sortOptionName) {
        this.sortOptionName = sortOptionName;
    }

    public String getSortOptionKey() {
        return sortOptionKey;
    }

    public void setSortOptionKey(String sortOptionKey) {
        this.sortOptionKey = sortOptionKey;
    }
}
