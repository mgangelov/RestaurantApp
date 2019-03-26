package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("_id")})
public class EncryptedMessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "salt", typeAffinity = ColumnInfo.BLOB)
    public byte[] salt;

    @ColumnInfo(name = "encrypted_message", typeAffinity = ColumnInfo.BLOB)
    public byte[] ciphertext;

    @ColumnInfo(name = "iv_spec", typeAffinity = ColumnInfo.BLOB)
    public byte[] ivSpec;

    public EncryptedMessageEntity(
            byte[] salt,
            byte[] ciphertext,
            byte[] ivSpec
    ) {
        this.salt = salt;
        this.ciphertext = ciphertext;
        this.ivSpec = ivSpec;
    }
}
