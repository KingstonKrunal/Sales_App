package com.letsbiz.salesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    TextView mUserNameTextView, mEmailTextView, mTotalEntriesTextView;
    ImageButton mEditImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUserNameTextView = findViewById(R.id.userNameTextView);
        mEmailTextView = findViewById(R.id.emailTextView);
        mTotalEntriesTextView = findViewById(R.id.totalEntriesTextView);
        mEditImageButton = findViewById(R.id.editNameImageBtn);

        //TODO: Update Fields in the view
    }
}