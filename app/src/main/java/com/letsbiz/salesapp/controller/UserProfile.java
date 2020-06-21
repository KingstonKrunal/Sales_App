package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        mEditImageButton = findViewById(R.id.imgBtnEditName);

        setUpFields();

        mEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: edit name
            }
        });
    }

    void setUpFields() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mUserNameTextView.setText(user.getDisplayName());
        mEmailTextView.setText(user.getEmail());
    }
}