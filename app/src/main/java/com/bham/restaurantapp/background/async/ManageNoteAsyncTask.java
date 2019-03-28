package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.Globals;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
import com.bham.restaurantapp.security.AESDecryptor;
import com.bham.restaurantapp.security.AESEncryptor;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;


public class ManageNoteAsyncTask extends AsyncTask<Integer, Void, String> {
    private static final String TAG = "ManageNoteAsyncTask";
    private WeakReference<EditText> noteContentsEditText;
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;
    private Globals.NOTE_MODES mode;
    private WeakReference<Activity> noteActivity;
    private int establishmentId;
    private byte[] salt;
    private byte[] iv;

    public ManageNoteAsyncTask(Activity noteActivity, EditText noteContentsEditText, Globals.NOTE_MODES mode) {
        this.noteContentsEditText = new WeakReference<>(noteContentsEditText);
        this.db = App.getInstance().getDb();
        this.noteActivity = new WeakReference<>(noteActivity);
        this.sharedPreferences =
                noteActivity
                        .getApplicationContext()
                        .getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        this.mode = mode;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        EncryptedMessageEntity e =
                db.encryptedMessageDAO().findEncryptedMessageByEstablishmentId(integers[0]);
        establishmentId = integers[0];
        if (sharedPreferences.getString("passwordHash", null) != null) {
            if (mode.equals(Globals.NOTE_MODES.VIEW_MODE)) {
                if (db.encryptedMessageDAO().countRows() != 0 && db.encryptedMessageDAO().countRows(e._id) != 0) {
                    AESDecryptor aesDecryptor =
                            new AESDecryptor(
                                    e.salt,
                                    Objects.requireNonNull(sharedPreferences.getString("passwordHash", null)));
                    return new String(aesDecryptor.decrypt(e.ciphertext), StandardCharsets.UTF_8);
                } else {
                    return null;
                }
            }
            if (mode.equals((Globals.NOTE_MODES.SAVE_MODE))) {
                AESEncryptor aesEncryptor =
                        new AESEncryptor(Objects.requireNonNull(sharedPreferences.getString("passwordHash", null)));
                salt = aesEncryptor.getSalt();
                iv = aesEncryptor.getIv();
                Log.i(TAG, "doInBackground: ciphertext: " + Arrays.toString(aesEncryptor.encrypt(noteContentsEditText.get().getText().toString().getBytes(StandardCharsets.UTF_8))));
                return new String(aesEncryptor.encrypt(noteContentsEditText.get().getText().toString().getBytes(StandardCharsets.UTF_8)));
            }
        } else {
            throw new IllegalArgumentException("Password must be set to use the encrypted notes functionality");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mode.equals(Globals.NOTE_MODES.VIEW_MODE)) {
            noteContentsEditText.get().setText(s);
        }
        if (mode.equals(Globals.NOTE_MODES.SAVE_MODE)) {
            EncryptedMessageEntity currentNote = new EncryptedMessageEntity(
                    salt,
                    s.getBytes(),
                    iv,
                    establishmentId
            );
            db.encryptedMessageDAO().insertEncryptedMessageEntity(currentNote);
            Toast.makeText(noteActivity.get().getApplicationContext(), "Saved encrypted note", Toast.LENGTH_LONG)
                    .show();
            noteActivity.get().finish();
        }
    }
}
