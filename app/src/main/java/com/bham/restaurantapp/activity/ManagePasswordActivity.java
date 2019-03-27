package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bham.restaurantapp.R;
import com.google.android.material.button.MaterialButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ManagePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ManagePasswordActivity";
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_password);
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        TextView oldPasswordTextView = findViewById(R.id.oldPasswordTextView);
        MaterialButton updatePasswordButton = findViewById(R.id.submitPasswordMaterialButton);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        sharedPreferences =
                getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (sharedPreferences.getString("passwordHash", null) != null) {
            oldPasswordTextView.setVisibility(View.VISIBLE);
            oldPasswordEditText.setVisibility(View.VISIBLE);
            updatePasswordButton.setText(getString(R.string.update_password_button_text));
        } else {
            updatePasswordButton.setText(getString(R.string.set_password_button_text));
        }
    }

    public void setPassword(View view) {
        MessageDigest md;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getString("passwordHash", null) == null) {
            try {
                md = MessageDigest.getInstance("SHA-256");
                if (!newPasswordEditText.getText().toString().isEmpty()) {
                    byte[] passwordHash = md.digest(
                            newPasswordEditText.getText().toString().getBytes()
                    );
                    editor.putString("passwordHash", Arrays.toString(passwordHash));
                    editor.apply();
                    Log.i(TAG, "setPassword: Password set.");
                    Intent i = new Intent(this, SearchScreenActivity.class);
                    Toast.makeText(getApplicationContext(), "Password set", Toast.LENGTH_LONG)
                            .show();
                    startActivity(i);
                } else {
                    // TODO: new password empty
                    Toast.makeText(getApplicationContext(), "Please input a password", Toast.LENGTH_LONG)
                            .show();
                    Log.i(TAG, "setPassword: Please enter new password");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            try {
                md = MessageDigest.getInstance("SHA-256");
                if (!oldPasswordEditText.getText().toString().isEmpty()) {
                    byte[] oldPasswordHash = md.digest(
                            oldPasswordEditText.getText().toString().getBytes()
                    );
                    if (Objects.requireNonNull(
                            sharedPreferences.getString("passwordHash", null))
                            .equals(Arrays.toString(oldPasswordHash))
                    ) {
                        byte[] newPasswordHash = md.digest(
                                newPasswordEditText.getText().toString().getBytes()
                        );
                        editor.putString("passwordHash", Arrays.toString(newPasswordHash));
                        editor.apply();
                        Log.i(TAG, "setPassword: Password updated.");
                        Intent i = new Intent(this, SearchScreenActivity.class);
                        Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_LONG)
                                .show();
                        startActivity(i);
                        // TODO: Re-encrypt all notes now
                    } else {
                        // TODO: Wrong password
                        Toast.makeText(getApplicationContext(), "Old password is wrong, try again", Toast.LENGTH_LONG)
                                .show();
                        oldPasswordEditText.setText(null);
                        Log.i(TAG, "setPassword: WRONG PASSWORD");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please input your old password", Toast.LENGTH_LONG)
                            .show();
                    Log.i(TAG, "setPassword: please input old password");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    public void removePassword(View view) {
        MessageDigest md;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            md = MessageDigest.getInstance("SHA-256");
            if (!oldPasswordEditText.getText().toString().isEmpty()) {
                byte[] oldPasswordHash = md.digest(
                        oldPasswordEditText.getText().toString().getBytes()
                );
                if (Objects.requireNonNull(
                        sharedPreferences.getString("passwordHash", null))
                        .equals(Arrays.toString(oldPasswordHash))
                ) {
                    editor.remove("passwordHash");
                    editor.putBoolean("needPass", false);
                    editor.apply();
                    Log.i(TAG, "setPassword: Password deleted.");
                    Intent i = new Intent(this, SearchScreenActivity.class);
                    Toast.makeText(getApplicationContext(), "Password deleted", Toast.LENGTH_LONG)
                            .show();
                    startActivity(i);
                    // TODO: Re-encrypt all notes now
                } else {
                    // TODO: Wrong password
                    Toast.makeText(getApplicationContext(), "Old password is wrong, try again", Toast.LENGTH_LONG)
                            .show();
                    oldPasswordEditText.setText(null);
                    Log.i(TAG, "setPassword: WRONG PASSWORD");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please input your old password", Toast.LENGTH_LONG)
                        .show();
                Log.i(TAG, "setPassword: please input old password");
            }
    } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
