package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESDecryptor;
import com.bham.restaurantapp.security.AESEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;


public class MassReencryptNotesAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "MassReencryptNotesAsyncTask";
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;

    public MassReencryptNotesAsyncTask(Activity noteActivity) {
        this.db = App.getInstance().getDb();
        this.sharedPreferences =
                noteActivity
                        .getApplicationContext()
                        .getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Void... integers) {
        if (sharedPreferences.getString("passwordHash", null) != null && sharedPreferences.getString("oldPasswordHash", null) != null) {
            if (db.encryptedMessageDAO().countRows() != 0) {
                List<EncryptedMessageEntity> notes = db.encryptedMessageDAO().getAll();
                for (EncryptedMessageEntity e: notes) {
                    AESDecryptor aesDecryptor =
                            new AESDecryptor(
                                    e.salt,
                                    Objects.requireNonNull(sharedPreferences.getString("oldPasswordHash", null)));
                    AESEncryptor aesEncryptor =
                            new AESEncryptor(Objects.requireNonNull(sharedPreferences.getString("passwordHash", null)));
                    Log.i(TAG, "doInBackground: the decrypted value: " + new String(aesDecryptor.decrypt(e.ciphertext), StandardCharsets.UTF_8));
                    byte[] plaintext = aesDecryptor.decrypt(e.ciphertext);
                    EncryptedMessageEntity newE = new EncryptedMessageEntity(
                            aesEncryptor.getSalt(),
                            aesEncryptor.encrypt(plaintext),
                            aesEncryptor.getIv(),
                            e.establishmentId
                    );
                    newE._id = e._id;
                    db.encryptedMessageDAO().insertEncryptedMessageEntity(newE);
                }
            } else {
                Log.i(TAG, "doInBackground: couldn't find anything in db");
                return null;
            }
        } else {
            throw new IllegalArgumentException("Password must be set to use the encrypted notes functionality");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void s) {
        Log.i(TAG, "onPostExecute: Received stirng is: " + s);
        super.onPostExecute(s);
    }
}
