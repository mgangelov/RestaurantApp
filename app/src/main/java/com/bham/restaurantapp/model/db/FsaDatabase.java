package com.bham.restaurantapp.model.db;

import com.bham.restaurantapp.model.db.dao.BusinessTypeDAO;
import com.bham.restaurantapp.model.db.entities.BusinessTypeEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BusinessTypeEntity.class}, version = 1, exportSchema = false)
public abstract class FsaDatabase extends RoomDatabase {
    public abstract BusinessTypeDAO businessTypeDAO();
}
