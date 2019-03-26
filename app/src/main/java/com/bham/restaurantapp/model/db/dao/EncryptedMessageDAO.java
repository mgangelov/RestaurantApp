package com.bham.restaurantapp.model.db.dao;

import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EncryptedMessageDAO {
    @Query("select * from encryptedmessageentity")
    List<EncryptedMessageEntity> getAll();

    @Query("select * from encryptedmessageentity where _id like :emId")
    EncryptedMessageEntity findEncryptedMessageById(int emId);

    @Insert
    long insertEncryptedMessageEntity(EncryptedMessageEntity emt);

    @Query("delete from encryptedmessageentity")
    void deleteAllEncryptedMessageEntities();
}
