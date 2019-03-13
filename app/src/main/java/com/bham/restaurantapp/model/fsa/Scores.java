package com.bham.restaurantapp.model.fsa;

import com.google.gson.annotations.SerializedName;

public class Scores {
    @SerializedName("Hygiene")
    private String hygiene;
    @SerializedName("Structural")
    private String structural;
    @SerializedName("ConfidenceManagement")
    private String confidenceInManagement;

    public String getHygiene() {
        return hygiene;
    }

    public void setHygiene(String hygiene) {
        this.hygiene = hygiene;
    }

    public String getStructural() {
        return structural;
    }

    public void setStructural(String structural) {
        this.structural = structural;
    }

    public String getConfidenceInManagement() {
        return confidenceInManagement;
    }

    public void setConfidenceInManagement(String confidenceInManagement) {
        this.confidenceInManagement = confidenceInManagement;
    }
}
