package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESDecryptor;

import java.util.Arrays;

public class DecryptMessageAsyncTask extends AsyncTask<Long, Void, Byte[]> {
    private static final String TAG = "DecryptMessageAsyncTask";
    private FsaDatabase db;

    public DecryptMessageAsyncTask() {
        this.db = App.getInstance().getDb();
    }

    @Override
    protected Byte[] doInBackground(Long... longs) {
        Log.i(TAG, "doInBackground: Encrypted message id is " + longs[0]);
        EncryptedMessageEntity e =
                db.encryptedMessageDAO().findEncryptedMessageById(Math.toIntExact(longs[0]));
        AESDecryptor aesDecryptor = new AESDecryptor(e.ivSpec, e.salt, e.ciphertext);
        Log.i(TAG, "doInBackground: Plaintext is " + aesDecryptor.decryptCiphertext(e.ciphertext));

        return convertPrimitiveToNonPrimitive(aesDecryptor.decryptCiphertext(e.ciphertext));
    }

    private Byte[] convertPrimitiveToNonPrimitive(byte[] a) {
        int counter = 0;
        Byte[] bytes = new Byte[a.length];
        for (byte b: a)
            bytes[counter++] = b;
        return bytes;
    }

    @Override
    protected void onPostExecute(Byte[] plaintext) {
        super.onPostExecute(plaintext);
        Log.i(TAG, "onPostExecute: Plaintext is " + Arrays.toString(plaintext));
    }

}
