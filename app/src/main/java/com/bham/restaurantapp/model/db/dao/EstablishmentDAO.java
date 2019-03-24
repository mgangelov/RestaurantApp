package com.bham.restaurantapp.model.db.dao;

import android.database.Cursor;

import com.bham.restaurantapp.model.db.entities.EstablishmentEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EstablishmentDAO {

    @Query("select * from establishmententity")
    List<EstablishmentEntity> getAll();

    @Query("select * from establishmententity order by business_name asc")
    Cursor getAllCursor();

    @Query("select * from establishmententity where _id like :fhrsId")
    EstablishmentEntity findEstablishmentById(int fhrsId);

    @Insert
    void insertEstablishmentEntity(EstablishmentEntity e);

    @Query("delete from establishmententity")
    void deleteAllEstablishmentEntities();

    @Delete
    void deleteEstablishmentEntity(EstablishmentEntity e);
}
