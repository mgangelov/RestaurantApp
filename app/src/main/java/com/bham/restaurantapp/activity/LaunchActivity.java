package com.bham.restaurantapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);
        new RefreshDbAsyncTask(getApplicationContext())
                .execute();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (sharedPreferences.getString("passwordHash", null) != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("needPass", true);
            editor.apply();
        }
    }
}
