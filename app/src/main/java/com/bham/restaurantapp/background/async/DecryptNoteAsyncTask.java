package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESDecryptor;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class DecryptNoteAsyncTask extends AsyncTask<Integer, Void, String> {
    private static final String TAG = "DecryptNoteAsyncTask";
    private WeakReference<EditText> noteContentsEditText;
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;
    private byte[] salt;
    private byte[] iv;

    public DecryptNoteAsyncTask(Activity noteActivity, EditText noteContentsEditText) {
        this.noteContentsEditText = new WeakReference<>(noteContentsEditText);
        this.db = App.getInstance().getDb();
        this.sharedPreferences =
                noteActivity
                        .getApplicationContext()
                        .getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    protected String doInBackground(Integer... integers) {
        EncryptedMessageEntity e =
                db.encryptedMessageDAO().findEncryptedMessageByEstablishmentId(integers[0]);
        if (sharedPreferences.getString("passwordHash", null) != null) {
            if (db.encryptedMessageDAO().countRows() != 0 && db.encryptedMessageDAO().countRows(integers[0]) != 0) {
                AESDecryptor aesDecryptor =
                        new AESDecryptor(
                                e.salt,
                                Objects.requireNonNull(sharedPreferences.getString("passwordHash", null)));
                Log.i(TAG, "doInBackground: the decrypted value: " + new String(aesDecryptor.decrypt(e.ciphertext), StandardCharsets.UTF_8));
                return new String(aesDecryptor.decrypt(e.ciphertext), StandardCharsets.UTF_8);
            } else {
                Log.i(TAG, "doInBackground: couldn't find anything in db");
                return null;
            }
        } else {
            throw new IllegalArgumentException("Password must be set to use the encrypted notes functionality");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, "onPostExecute: Received stirng is: " + s);
        super.onPostExecute(s);
        noteContentsEditText.get().setText(s);
    }
}
