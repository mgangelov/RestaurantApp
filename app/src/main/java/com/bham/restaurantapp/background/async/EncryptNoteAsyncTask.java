package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESEncryptor;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;


public class EncryptNoteAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = "EncryptNoteAsyncTask";
    private WeakReference<EditText> noteContentsEditText;
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;
    private WeakReference<Activity> noteActivity;

    public EncryptNoteAsyncTask(Activity noteActivity, EditText noteContentsEditText) {
        this.noteContentsEditText = new WeakReference<>(noteContentsEditText);
        this.db = App.getInstance().getDb();
        this.noteActivity = new WeakReference<>(noteActivity);
        this.sharedPreferences =
                noteActivity
                        .getApplicationContext()
                        .getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        EncryptedMessageEntity e =
                db.encryptedMessageDAO().findEncryptedMessageByEstablishmentId(integers[0]);
        if (sharedPreferences.getString("passwordHash", null) != null) {
            AESEncryptor aesEncryptor =
                    new AESEncryptor(Objects.requireNonNull(sharedPreferences.getString("passwordHash", null)));
            Log.i(TAG, "doInBackground: ciphertext: " + Arrays.toString(aesEncryptor.encrypt(noteContentsEditText.get().getText().toString().getBytes(StandardCharsets.UTF_8))));
            EncryptedMessageEntity currentNote =
                    new EncryptedMessageEntity(
                            aesEncryptor.getSalt(),
                            aesEncryptor.encrypt(noteContentsEditText.get().getText().toString().getBytes(StandardCharsets.UTF_8)),
                            aesEncryptor.getIv(),
                            integers[0]
                    );
            Log.i(TAG, "doInBackground: created currentNote");
            if (e != null) {
                currentNote._id = e._id;
            }
            db.encryptedMessageDAO().insertEncryptedMessageEntity(currentNote);
            return null;

        } else {
            throw new IllegalArgumentException("Password must be set to use the encrypted notes functionality");
        }
    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
        Toast.makeText(noteActivity.get().getApplicationContext(), "Saved encrypted note", Toast.LENGTH_LONG)
                .show();
        noteActivity.get().finish();
    }
}
