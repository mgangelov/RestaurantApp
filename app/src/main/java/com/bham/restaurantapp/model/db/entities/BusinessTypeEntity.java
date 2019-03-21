package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BusinessTypeEntity {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "business_type_id")
    public int btId;

    @ColumnInfo(name = "business_type_name")
    public String btName;
}
