package com.bham.restaurantapp.model.fsa;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Establishment implements Parcelable {
    @SerializedName("FHRSID")
    private String fhrsId;
    @SerializedName("BusinessName")
    private String businessName;
    @SerializedName("BusinessTypeID")
    private String businessTypeID;
    @SerializedName("BusinessType")
    private String businessType;
    @SerializedName("AddressLine1")
    private String addressLine1;
    @SerializedName("AddressLine2")
    private String addressLine2;
    @SerializedName("AddressLine3")
    private String addressLine3;
    @SerializedName("AddressLine4")
    private String addressLine4;
    @SerializedName("PostCode")
    private String postCode;
    @SerializedName("RatingValue")
    private String ratingValue;
    @SerializedName("RatingDate")
    private String ratingDate;
    @SerializedName("scores")
    private Scores scores;
    @SerializedName("geocode")
    private GeoCode geocode;
    @SerializedName("Distance")
    private String distance;
    @SerializedName("LocalAuthorityName")
    private String localAuthorityName;
    private ResponseMetadata meta;

    protected Establishment(Parcel in) {
        fhrsId = in.readString();
        businessName = in.readString();
        businessTypeID = in.readString();
        addressLine1 = in.readString();
        addressLine2 = in.readString();
        addressLine3 = in.readString();
        addressLine4 = in.readString();
        postCode = in.readString();
        ratingValue = in.readString();
        ratingDate = in.readString();
        distance = in.readString();
    }

    public static final Creator<Establishment> CREATOR = new Creator<Establishment>() {
        @Override
        public Establishment createFromParcel(Parcel in) {
            return new Establishment(in);
        }

        @Override
        public Establishment[] newArray(int size) {
            return new Establishment[size];
        }
    };

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ResponseMetadata getMeta() {
        return meta;
    }

    public void setMeta(ResponseMetadata meta) {
        this.meta = meta;
    }

    public void setFhrsid(String fhrsid) {
        this.fhrsId = fhrsid;
    }

    public String getFhrsId() {
        return fhrsId;
    }

    public void setFhrsId(String fhrsId) {
        this.fhrsId = fhrsId;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public GeoCode getGeocode() {
        return geocode;
    }

    public void setGeocode(GeoCode geocode) {
        this.geocode = geocode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(String businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fhrsId);
        dest.writeString(businessName);
        dest.writeString(businessTypeID);
        dest.writeString(addressLine1);
        dest.writeString(addressLine2);
        dest.writeString(addressLine3);
        dest.writeString(addressLine4);
        dest.writeString(postCode);
        dest.writeString(ratingValue);
        dest.writeString(ratingDate);
        dest.writeString(distance);
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getLocalAuthorityName() {
        return localAuthorityName;
    }

    public void setLocalAuthorityName(String localAuthorityName) {
        this.localAuthorityName = localAuthorityName;
    }
}
