package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = RegionEntity.class,
        parentColumns = "_id",
        childColumns = "authority_region_id"
),
        indices = {@Index("authority_region_id"), @Index("_id")}
)
public class AuthorityEntity {
    @PrimaryKey
    public int _id;
    @ColumnInfo(name = "authority_name")
    public String name;
    @ColumnInfo(name = "authority_region_id")
    public Integer regionId;

    public AuthorityEntity(int _id, String name, Integer regionId) {
        this._id = _id;
        this.name = name;
        this.regionId = regionId;
    }
}
