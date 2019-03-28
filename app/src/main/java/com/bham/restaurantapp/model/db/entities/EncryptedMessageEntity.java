package com.bham.restaurantapp.model.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = EstablishmentEntity.class,
        parentColumns = "_id",
        childColumns = "establishment_id"
),
        indices = {@Index("establishment_id"), @Index("_id")}
)
public class EncryptedMessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "salt", typeAffinity = ColumnInfo.BLOB)
    public byte[] salt;

    @ColumnInfo(name = "encrypted_message", typeAffinity = ColumnInfo.BLOB)
    public byte[] ciphertext;

    @ColumnInfo(name = "iv_spec", typeAffinity = ColumnInfo.BLOB)
    public byte[] ivSpec;

    @ColumnInfo(name= "establishment_id")
    public int establishmentId;

    public EncryptedMessageEntity(
            byte[] salt,
            byte[] ciphertext,
            byte[] ivSpec,
            int establishmentId
    ) {
        this.salt = salt;
        this.ciphertext = ciphertext;
        this.ivSpec = ivSpec;
        this.establishmentId = establishmentId;
    }
}
