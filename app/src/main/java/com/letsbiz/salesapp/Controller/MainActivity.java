package com.letsbiz.salesapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Email","");

        if (name.isEmpty()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(MainActivity.this, HomeScreen.class));
            finish();
        }
    }
}