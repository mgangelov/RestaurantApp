package com.bham.restaurantapp.model.db.entities;

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
    @ColumnInfo(name = "address_line")
    public String addressLine;
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
            String addressLine,
            int ratingValue,
            String ratingDate,
            String localAuthorityName
    ) {
        this._id = _id;
        this.businessName = businessName;
        this.businessType = businessType;
        this.businessTypeId = businessTypeId;
        this.addressLine = addressLine;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.localAuthorityName = localAuthorityName;
    }
}
