package com.bham.restaurantapp.model.db.entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("_id")})
public class EstablishmentEntity {
    @PrimaryKey
    public int _id;

    @ColumnInfo(name = "business_name")
    public String businessName;
    @ColumnInfo(name = "business_type")
    public String businessType;
    @ColumnInfo(name = "business_type_id")
    public int businessTypeId;
    @Nullable
    @ColumnInfo(name = "address_line_1")
    public String addressLine1;
    @Nullable
    @ColumnInfo(name = "address_line_2")
    public String addressLine2;
    @Nullable
    @ColumnInfo(name = "address_line_3")
    public String addressLine3;
    @Nullable
    @ColumnInfo(name = "address_line_4")
    public String addressLine4;
    @Nullable
    @ColumnInfo(name = "postcode")
    public String postCode;
    @ColumnInfo(name = "rating_value")
    public int ratingValue;
    @ColumnInfo(name = "rating_date")
    public String ratingDate;
    @ColumnInfo(name = "local_authority_name")
    public String localAuthorityName;


    public EstablishmentEntity(
            int _id,
            String businessName,
            String businessType,
            int businessTypeId,
            String addressLine1,
            String addressLine2,
            String addressLine3,
            String addressLine4,
            String postCode,
            int ratingValue,
            String ratingDate,
            String localAuthorityName) {
        this._id = _id;
        this.businessName = businessName;
        this.businessType = businessType;
        this.businessTypeId = businessTypeId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.addressLine4 = addressLine4;
        this.postCode = postCode;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.localAuthorityName = localAuthorityName;
    }
}
