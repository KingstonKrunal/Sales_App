package com.letsbiz.salesapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.letsbiz.salesapp.R;

public class UserProfile extends AppCompatActivity {

    TextView mUserNameTextView, mEmailTextView, mTotalEntriesTextView;
    ImageButton mEditImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUserNameTextView = findViewById(R.id.userNameTextView);
        mEmailTextView = findViewById(R.id.emailTextView);
        mTotalEntriesTextView = findViewById(R.id.totalEntriesTextView);
        mEditImageButton = findViewById(R.id.editNameImageBtn);

        //TODO: Update Fields in the view
    }
}