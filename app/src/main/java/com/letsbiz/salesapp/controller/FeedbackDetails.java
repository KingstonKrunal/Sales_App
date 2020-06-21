package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.databinding.ActivityFeedbackDetailsBinding;

public class FeedbackDetails extends AppCompatActivity {

    static public String FEEDBACK = "feedback";
    static public String FEEDBACK_ID = "feedback_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);

        Intent i = getIntent();
        Feedback feedback = (Feedback) i.getSerializableExtra(FEEDBACK);
        String feedbackId = i.getStringExtra(FEEDBACK_ID);

        ActivityFeedbackDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback_details);
        binding.setFeedback(feedback);
    }

}