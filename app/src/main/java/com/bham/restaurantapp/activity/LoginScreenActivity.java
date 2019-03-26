package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EncryptMessageAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreenActivity extends AppCompatActivity {
    private static final String TAG = "LoginScreenActivity";
    private EditText textEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        textEditText = findViewById(R.id.textEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void submitPassword(View view) {
        // Get inputted password as byte array, we don't want to have it
        // as String in memory

        int passwordLength = passwordEditText.length();
        char[] passwordInput = new char[passwordLength];
        passwordEditText.getText().getChars(
                0,
                passwordLength,
                passwordInput,
                0
        );
        CharSequence plaintext = textEditText.getText().toString();
        new EncryptMessageAsyncTask()
                .execute(
                        new String(passwordInput),
                        plaintext
                );


    }
}
