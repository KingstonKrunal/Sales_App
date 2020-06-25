package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.databinding.ActivityFeedbackDetailsBinding;
import com.letsbiz.salesapp.model.UserList;

public class FeedbackDetailsActivity extends AppCompatActivity {
    static public String FEEDBACK = "feedback";
    static public String FEEDBACK_ID = "feedback_id";
    private ActivityFeedbackDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback_details);

        Intent i = getIntent();
        final Feedback feedback = (Feedback) i.getSerializableExtra(FEEDBACK);
        String feedbackId = i.getStringExtra(FEEDBACK_ID); // For Edit from this activity
        boolean isAdmin = i.getBooleanExtra("isAdmin", false);

        if (feedback != null) {
            mBinding.setFeedback(feedback);
            mBinding.setUserName(UserList.getInstance().getUserName(feedback.getUid()));

            UserList.getInstance().setListener(new UserList.UserListUpdateListener() {
                @Override
                public void onUpdate() {
                    mBinding.textView47.setText(UserList.getInstance().getUserName(feedback.getUid()));
                }
            });
        }

        mBinding.setIsAdmin(isAdmin);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle("Feedback Details");
        }
    }
}