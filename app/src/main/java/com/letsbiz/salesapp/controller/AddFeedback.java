package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.model.Callback;
import com.letsbiz.salesapp.model.FeedbackRepository;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.model.Utility;

import org.jetbrains.annotations.NotNull;

public class AddFeedback extends AppCompatActivity {
    EditText mShopName, mShopOwnerName, mOwnerSug, mUserSug, mShopCategory;
    Button mSaveButton;
    RadioGroup mAppDownloadedRadioG, mRegisteredRadioG;
    RatingBar mRatingBar;
    ProgressBar mProgressBar;

    private boolean mIsEditing = false;
    private String mFeedbackKey = "";

    String[] shopCategoryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        shopCategoryArray = getResources().getStringArray(R.array.shopCategory);

        mShopName = findViewById(R.id.shopNameEditText);
        mShopOwnerName = findViewById(R.id.shopOwnerNameEdittext);
        mShopCategory = findViewById(R.id.editTextShopCategory);
        mOwnerSug = findViewById(R.id.theirSuggEditText);
        mUserSug = findViewById(R.id.yourSuggEditText);
        mSaveButton = findViewById(R.id.saveButton);
        mProgressBar = findViewById(R.id.progressBar);
        mRegisteredRadioG = findViewById(R.id.registeredRadioGroup);
        mAppDownloadedRadioG = findViewById(R.id.downloadedradioGroup);
        mRatingBar = findViewById(R.id.ratingBar);

        mProgressBar.setActivated(false);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle("New Feedback");
        }

        setUpListeners();

        setUpFieldValuesIfRequired();
    }

    void setUpListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();

                Feedback feedback = new Feedback();
                feedback.setShopName(mShopName.getText().toString());
                feedback.setOwnerName(mShopOwnerName.getText().toString());
                feedback.setShopCategory(mShopCategory.getText().toString());
                feedback.setShopOwnerSug(mOwnerSug.getText().toString());
                feedback.setUserSug(mUserSug.getText().toString());
                feedback.setIsInstalled(
                        mAppDownloadedRadioG.getCheckedRadioButtonId() == R.id.down_yes ? "Yes" : "No"
                );
                feedback.setIsRegistered(
                        mRegisteredRadioG.getCheckedRadioButtonId() == R.id.reg_yes ? "Yes" : "No"
                );
                feedback.setRatings(mRatingBar.getRating());

                String invalidFields = feedback.invalidFields();
                if(!invalidFields.isEmpty()) {
                    Toast.makeText(AddFeedback.this, "Please enter " + invalidFields, Toast.LENGTH_SHORT).show();
                    hideProgress();
                    return;
                }

                Callback callback = new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        finish();
                    }

                    @Override
                    public void onError(Object object) {
                        Toast.makeText(AddFeedback.this, "Something Unexpected occurred", Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }
                };

                FeedbackRepository repository = new FeedbackRepository();

                if(mIsEditing) {
                    repository.updateFeedback(mFeedbackKey, feedback, callback);
                } else {
                    repository.createFeedback(feedback, callback);
                }

            }
        });
    }

    void setUpFieldValuesIfRequired() {
        Intent i = getIntent();
        String id = i.getStringExtra(FeedbackDetails.FEEDBACK_ID);

        if(!Utility.isEmptyOrNull(id)) {
            Feedback feedback = (Feedback) i.getSerializableExtra(FeedbackDetails.FEEDBACK);
            if(feedback == null) return;

            mIsEditing = true;
            mFeedbackKey = id;

            mShopName.setText(feedback.getShopName());
            mShopOwnerName.setText(feedback.getOwnerName());
            mShopCategory.setText(feedback.getShopCategory());
            mOwnerSug.setText(feedback.getShopOwnerSug()); ;
            mUserSug.setText(feedback.getUserSug());
            mRatingBar.setRating(feedback.getRatings());

            mRegisteredRadioG.check(feedback.getIsRegistered().equals("Yes") ? R.id.reg_yes: R.id.reg_no);
            mAppDownloadedRadioG.check(feedback.getIsInstalled().equals("Yes") ? R.id.down_yes: R.id.down_no);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_feedback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.close_menu_item) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        mSaveButton.setAlpha(0);
        mSaveButton.setActivated(false);
        mProgressBar.setAlpha(1);
        mProgressBar.setActivated(true);
    }
    public void hideProgress() {
        mSaveButton.setAlpha(1);
        mSaveButton.setActivated(true);
        mProgressBar.setAlpha(0);
        mProgressBar.setActivated(false);
    }

}