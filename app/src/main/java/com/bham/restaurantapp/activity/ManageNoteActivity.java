package com.bham.restaurantapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.DecryptNoteAsyncTask;
import com.bham.restaurantapp.background.async.EncryptNoteAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class ManageNoteActivity extends AppCompatActivity {
    private EditText noteTextEditText;
    private SharedPreferences sharedPreferences;
    private int establishmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);
        noteTextEditText = findViewById(R.id.noteContentsEditText);
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        establishmentId = getIntent().getIntExtra("establishmentId", 0);
        new DecryptNoteAsyncTask(this, noteTextEditText)
                .execute(establishmentId);
    }

    public void removeNote(View view) {
        if (sharedPreferences.getString("passwordHash", null) == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_to_set_password), Toast.LENGTH_LONG)
                    .show();
        } else {
        }
    }

    public void saveNote(View view) {
        if (sharedPreferences.getString("passwordHash", null) == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_to_set_password), Toast.LENGTH_LONG)
                    .show();
        } else {
            new EncryptNoteAsyncTask(this, noteTextEditText)
                    .execute(establishmentId);
        }
    }
}
