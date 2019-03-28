package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESDecryptor;

import java.nio.charset.StandardCharsets;

public class DecryptMessageAsyncTask extends AsyncTask<Long, Void, String> {
    private static final String TAG = "DecryptMessageAsyncTask";
    private FsaDatabase db;
    private String masterKey;

    public DecryptMessageAsyncTask(String masterKey) {
        this.db = App.getInstance().getDb();
        this.masterKey = masterKey;
    }

    @Override
    protected String doInBackground(Long... longs) {
        Log.i(TAG, "doInBackground: Encrypted message id is " + longs[0]);
        EncryptedMessageEntity e =
                db.encryptedMessageDAO().findEncryptedMessageById(longs[0]);
        AESDecryptor aesDecryptor = new AESDecryptor(e.salt, masterKey);
        return new String(
                aesDecryptor.decrypt(e.ciphertext), StandardCharsets.UTF_8
        );

    }

    @Override
    protected void onPostExecute(String plaintext) {
        super.onPostExecute(plaintext);
        Log.i(TAG, "onPostExecute: Plaintext is " + plaintext);
    }

}
