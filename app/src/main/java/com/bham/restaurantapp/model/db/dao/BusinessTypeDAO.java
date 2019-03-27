package com.bham.restaurantapp.model.db.dao;

import android.database.Cursor;

import com.bham.restaurantapp.model.db.entities.BusinessTypeEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BusinessTypeDAO {
    @Query("select * from businesstypeentity")
    List<BusinessTypeEntity> getAll();

    @Query("select * from businesstypeentity order by business_type_name asc")
    Cursor getAllCursor();

    @Query("select * from businesstypeentity where _id like :btId")
    BusinessTypeEntity findBusinessTypeById(int btId);

    @Insert
    void insertBusinessTypeEntity(BusinessTypeEntity bte);

    @Query("select count(*) from businesstypeentity")
    int countRows();

    @Query("delete from businesstypeentity")
    void deleteAllBusinessTypeEntities();
}
