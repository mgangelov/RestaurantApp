package com.bham.restaurantapp.model.db.dao;

import android.database.Cursor;

import com.bham.restaurantapp.model.db.entities.SortOptionEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SortOptionsDAO {
    @Query("select * from sortoptionentity")
    List<SortOptionEntity> getAll();

    @Query("select * from sortoptionentity order by sort_option_name asc")
    Cursor getAllCursor();

    @Query("select * from sortoptionentity where _id like :soId")
    SortOptionEntity findSortOptionById(int soId);

    @Insert
    void insertSortOptionEntity(SortOptionEntity soE);

    @Query("delete from sortoptionentity")
    void deleteAllSortOptionsEntities();

}
