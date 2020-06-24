package com.letsbiz.salesapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AddFeedback extends AppCompatActivity {
    EditText mShopName, mShopOwnerName, mOwnerSug, mUserSug;
    AutoCompleteTextView mShopCategory;
    Button mSaveButton;
    RadioGroup mAppDownloadedRadioG, mRegisteredRadioG;
    RatingBar mRatingBar;
    ProgressBar mProgressBar;

    private String mFeedbackId = "";

    private Feedback mFeedback;
    private RequestMode mRequestMode;
    private String[] mCategoriesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        mShopName = findViewById(R.id.shopNameEditText);
        mShopOwnerName = findViewById(R.id.shopOwnerNameEdittext);
        mShopCategory = findViewById(R.id.acEditTextCategory);
        mOwnerSug = findViewById(R.id.theirSuggEditText);
        mUserSug = findViewById(R.id.yourSuggEditText);
        mSaveButton = findViewById(R.id.saveButton);
        mProgressBar = findViewById(R.id.progressBar);
        mRegisteredRadioG = findViewById(R.id.registeredRadioGroup);
        mAppDownloadedRadioG = findViewById(R.id.downloadedradioGroup);
        mRatingBar = findViewById(R.id.ratingBar);

        mProgressBar.setActivated(false);

        setUpListeners();

        setActivityInterface();

        mCategoriesArray = getResources().getStringArray(R.array.shopCategory);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mCategoriesArray);

        mShopCategory.setThreshold(2);
        mShopCategory.setAdapter(categoriesAdapter);
    }

    String getNonEmptyString(String s) {
        return Utility.isEmptyOrNull(s) ? "None" : s;
    }

    boolean validate() {
        boolean isValid = true;
        if(TextUtils.isEmpty(mShopName.getText())) {
            isValid = false;
            mShopName.setError("Shop name is mandatory");
        }
        if(TextUtils.isEmpty(mShopOwnerName.getText())) {
            isValid = false;
            mShopOwnerName.setError("Owner name is mandatory");
        }
        if(TextUtils.isEmpty(mShopCategory.getText())) {
            isValid = false;
            mShopCategory.setError("Category is mandatory");
        } else {
            boolean flag = false;
            String text = mShopCategory.getText().toString();

            for (String c :
                    mCategoriesArray) {
                if(c.compareTo(text) == 0) {
                    flag = true;
                    break;
                }
            }

            if(!flag) {
                mShopCategory.setError("Select valid Category");
                isValid = false;
            }
        }

        return isValid;
    }

    void setUpListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()) return;

                showProgress();

                Feedback feedback = new Feedback();
                feedback.setShopName(mShopName.getText().toString());
                feedback.setOwnerName(mShopOwnerName.getText().toString());
                feedback.setShopCategory(mShopCategory.getText().toString());
                feedback.setShopOwnerSug(getNonEmptyString(mOwnerSug.getText().toString()));
                feedback.setUserSug(getNonEmptyString(mUserSug.getText().toString()));
                feedback.setIsInstalled(
                        mAppDownloadedRadioG.getCheckedRadioButtonId() == R.id.down_yes ? "Yes" : "No"
                );
                feedback.setIsRegistered(
                        mRegisteredRadioG.getCheckedRadioButtonId() == R.id.reg_yes ? "Yes" : "No"
                );
                feedback.setRatings(mRatingBar.getRating());

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

                switch (mRequestMode) {
                    case Create:
                        repository.createFeedback(feedback, callback);
                        break;
                    case Edit:
                        repository.updateFeedback(mFeedbackId, feedback, callback);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    void setActivityInterface() {
        mRequestMode = getCurrentRequestMode();
        switch (mRequestMode) {
            case Edit:
                setUpFieldValues();
                break;
            case Create:
                break;
        }

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle(mRequestMode.toString() + " Feedback");
        }
    }

    RequestMode getCurrentRequestMode() {
        Intent i = getIntent();
        String id = i.getStringExtra(FeedbackDetails.FEEDBACK_ID);
        if(!Utility.isEmptyOrNull(id)) {
            mFeedbackId = id;
            mFeedback = (Feedback) i.getSerializableExtra(FeedbackDetails.FEEDBACK);
            return RequestMode.Edit;
        } else {
            return RequestMode.Create;
        }
    }

    void setUpFieldValues() {
            if(mFeedback == null) return;

            mShopName.setText(mFeedback.getShopName());
            mShopOwnerName.setText(mFeedback.getOwnerName());
            mShopCategory.setText(mFeedback.getShopCategory());
            mOwnerSug.setText(mFeedback.getShopOwnerSug());
            mUserSug.setText(mFeedback.getUserSug());
            mRatingBar.setRating(mFeedback.getRatings());

            mRegisteredRadioG.check(mFeedback.getIsRegistered().equals("Yes") ? R.id.reg_yes: R.id.reg_no);
            mAppDownloadedRadioG.check(mFeedback.getIsInstalled().equals("Yes") ? R.id.down_yes: R.id.down_no);
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

    private enum RequestMode {
        Edit, Create;

        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case Edit:
                    return "Edit";
                case Create:
                    return "Add";
                default:
                    return "";
            }
        }
    }


}