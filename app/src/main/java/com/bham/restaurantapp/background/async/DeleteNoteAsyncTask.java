package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;

import java.lang.ref.WeakReference;


public class DeleteNoteAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = "DeleteNoteAsyncTask";
    private WeakReference<EditText> noteContentsEditText;
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;
    private byte[] salt;
    private byte[] iv;
    private WeakReference<Activity> noteActivity;

    public DeleteNoteAsyncTask(Activity noteActivity, EditText noteContentsEditText) {
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
        if (sharedPreferences.getString("passwordHash", null) != null) {
            if (db.encryptedMessageDAO().countRows() != 0 && db.encryptedMessageDAO().countRows(integers[0]) != 0) {
                db.encryptedMessageDAO().deleteEncryptedMessageForEstablishmentId(integers[0]);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Password must be set to use the encrypted notes functionality");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
        Toast.makeText(noteActivity.get().getApplicationContext(), "Delete encrypted note", Toast.LENGTH_LONG)
                .show();
        noteActivity.get().finish();
    }
}
