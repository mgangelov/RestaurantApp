package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("_id")})
public class SortOptionEntity {

    @PrimaryKey
    public int _id;

    @ColumnInfo(name = "sort_option_name")
    public String sortOptionName;

    @ColumnInfo(name = "sort_option_key")
    public String sortOptionKey;

    public SortOptionEntity(int _id, String sortOptionName, String sortOptionKey) {
        this._id = _id;
        this.sortOptionName = sortOptionName;
        this.sortOptionKey = sortOptionKey;
    }
}
