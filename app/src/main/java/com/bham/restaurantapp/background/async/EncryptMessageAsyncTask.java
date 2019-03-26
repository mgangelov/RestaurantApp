package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESEncryptor;

import java.util.Arrays;

public class EncryptMessageAsyncTask extends AsyncTask<CharSequence, Void, Long> {
    private static final String TAG = "EncryptMessageAsyncTask";
    private FsaDatabase db;
    public EncryptMessageAsyncTask() {
        this.db = App.getInstance().getDb();
    }

    @Override
    protected Long doInBackground(CharSequence... charSequences) {
        AESEncryptor aes =
                new AESEncryptor(charSequences[0].toString().toCharArray());

        byte[] ciphertext = aes.
                encryptPlaintext(charSequences[1].toString().getBytes());

        Log.i(TAG, "submitPassword: Ciphertext is " +
                Arrays.toString(ciphertext)
        );
        return db.encryptedMessageDAO().insertEncryptedMessageEntity(
                new EncryptedMessageEntity(
                        aes.getSalt(),
                        ciphertext,
                        aes.getIvSpec().getIV()
                )
        );
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.i(TAG, "onPostExecute: The encrypted message id is: " + aLong);
        new DecryptMessageAsyncTask()
                .execute(aLong);
    }
}
