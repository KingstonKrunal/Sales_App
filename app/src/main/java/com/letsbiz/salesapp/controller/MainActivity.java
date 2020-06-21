package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.letsbiz.salesapp.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            User.setUID(user.getUid());
            startActivity(new Intent(MainActivity.this, HomeScreen.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}