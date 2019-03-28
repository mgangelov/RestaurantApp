package com.bham.restaurantapp.model.db.dao;

import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EncryptedMessageDAO {
    @Query("select * from encryptedmessageentity")
    List<EncryptedMessageEntity> getAll();

    @Query("select * from encryptedmessageentity where _id like :emId")
    EncryptedMessageEntity findEncryptedMessageById(Long emId);

    @Query("select * from encryptedmessageentity where establishment_id like :eId")
    EncryptedMessageEntity findEncryptedMessageByEstablishmentId(int eId);

    @Insert(onConflict = REPLACE)
    long insertEncryptedMessageEntity(EncryptedMessageEntity emt);

    @Query("delete from encryptedmessageentity")
    void deleteAllEncryptedMessageEntities();

    @Query("delete from encryptedmessageentity where establishment_id like :eId")
    void deleteEncryptedMessageForEstablishmentId(int eId);

    @Query("select count(*) from encryptedmessageentity")
    int countRows();

    @Query("select count(*) from encryptedmessageentity where establishment_id like :eId")
    int countRows(int eId);
}
