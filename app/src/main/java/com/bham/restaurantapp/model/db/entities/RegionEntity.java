package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("_id")})
public class RegionEntity {

    @PrimaryKey
    public int _id;

    @ColumnInfo(name = "region_name")
    public String name;

    @ColumnInfo(name = "region_name_key")
    public String nameKey;

    @ColumnInfo(name = "region_code")
    public String code;

    public RegionEntity(int _id, String name, String nameKey, String code) {
        this._id = _id;
        this.name = name;
        this.nameKey = nameKey;
        this.code = code;
    }
}
