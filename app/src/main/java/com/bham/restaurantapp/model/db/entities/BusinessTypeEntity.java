package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("_id")})
public class BusinessTypeEntity {

    @PrimaryKey
    public int _id;

    @ColumnInfo(name = "business_type_name")
    public String btName;

    public BusinessTypeEntity(int _id, String btName) {
        this._id = _id;
        this.btName = btName;
    }
}
