package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bham.restaurantapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreenActivity extends AppCompatActivity {
    private static final String TAG = "LoginScreenActivity";
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void submitPassword(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] inputPasswordHash = md.digest(
                    passwordEditText.getText().toString().getBytes()
            );
            if (Objects.requireNonNull(
                    sharedPreferences.getString("passwordHash", null))
                    .equals(Arrays.toString(inputPasswordHash))
            ) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("needPass", false);
                editor.apply();
                Intent i = new Intent(this, SearchScreenActivity.class);
                startActivity(i);
        } else {
                // TODO: Wrong password
                Log.i(TAG, "submitPassword: WRONG PASSOWRD");
                passwordEditText.setText(null);
                Toast.makeText(getApplicationContext(), "Wrong password, try again", Toast.LENGTH_LONG)
                        .show();
            }
    } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
