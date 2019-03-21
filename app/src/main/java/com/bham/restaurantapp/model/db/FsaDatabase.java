package com.bham.restaurantapp.model.db;

import com.bham.restaurantapp.model.db.dao.AuthorityDAO;
import com.bham.restaurantapp.model.db.dao.BusinessTypeDAO;
import com.bham.restaurantapp.model.db.dao.RegionDAO;
import com.bham.restaurantapp.model.db.entities.AuthorityEntity;
import com.bham.restaurantapp.model.db.entities.BusinessTypeEntity;
import com.bham.restaurantapp.model.db.entities.RegionEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        BusinessTypeEntity.class,
        RegionEntity.class,
        AuthorityEntity.class
}, version = 1, exportSchema = false)
public abstract class FsaDatabase extends RoomDatabase {
    public abstract BusinessTypeDAO businessTypeDAO();
    public abstract RegionDAO regionDAO();
    public abstract AuthorityDAO authorityDAO();
}
