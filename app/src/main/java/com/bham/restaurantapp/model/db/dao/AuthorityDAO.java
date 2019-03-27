package com.bham.restaurantapp.model.db.dao;

import android.database.Cursor;

import com.bham.restaurantapp.model.db.entities.AuthorityEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AuthorityDAO {

    @Query("select * from authorityentity")
    List<AuthorityEntity> getAll();

    @Query("select * from authorityentity order by authority_name asc")
    Cursor getAllCursor();

    @Query("select * from authorityentity\n" +
            "where _id < 9000 " +
            "order by authority_name asc")
    Cursor getAllFilteredCursor();

    @Query("select * from authorityentity where _id like :aeId")
    AuthorityEntity findAuthorityById(int aeId);

    @Query("select * from authorityentity " +
            "where authority_region_id like :regionId " +
            "order by authority_name asc")
    Cursor findAuthorityByRegionIdCursor(int regionId);

    @Query("select * from authorityentity " +
            "where authority_region_id like :regionId " +
            "order by authority_name asc")
    List<AuthorityEntity> findAuthorityByRegionId(int regionId);

    @Query("select count(*) from authorityentity")
    int countRows();

    @Insert
    void insertAuthorityEntity(AuthorityEntity ae);

    @Query("delete from authorityentity")
    void deleteAllAuthorityEntities();

}
