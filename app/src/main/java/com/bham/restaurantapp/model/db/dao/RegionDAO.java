package com.bham.restaurantapp.model.db.dao;

import android.database.Cursor;

import com.bham.restaurantapp.model.db.entities.RegionEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RegionDAO {
    @Query("select * from regionentity")
    List<RegionEntity> getAll();

    @Query("select * from regionentity order by region_name asc")
    Cursor getAllCursor();

    @Query("select * from regionentity where _id like :regionId")
    RegionEntity findRegionById(int regionId);

    @Insert
    void insertRegionEntity(RegionEntity regionEntity);

    @Query("delete from regionentity")
    void deleteRegionEntities();

    @Query("select count(*) from regionentity")
    int countRows();
}
