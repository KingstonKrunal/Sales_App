package com.letsbiz.salesapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.letsbiz.salesapp.Model.Feedback;
import com.letsbiz.salesapp.Model.Callback;
import com.letsbiz.salesapp.R;

public class AddFeedback extends AppCompatActivity {
    EditText mShopName, mShopOwnerName, mOwnerSug, mUserSug;
    Spinner mShopCategorySpinner;
    Button mSaveButton;
    RadioGroup mAppDownloadedRadioG, mRegisteredRadioG;
    RatingBar mRatingBar;
    ProgressBar mProgressBar;

    String[] shopCategoryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        shopCategoryArray = getResources().getStringArray(R.array.shopCategory);

        mShopName = findViewById(R.id.shopNameEditText);
        mShopOwnerName = findViewById(R.id.shopOwnerNameEdittext);
        mShopCategorySpinner = findViewById(R.id.shopCategorySpinner);
        mOwnerSug = findViewById(R.id.theirSuggEditText);
        mUserSug = findViewById(R.id.yourSuggEditText);
        mSaveButton = findViewById(R.id.saveButton);
        mProgressBar = findViewById(R.id.progressBar);
        mRegisteredRadioG = findViewById(R.id.registeredRadioGroup);
        mAppDownloadedRadioG = findViewById(R.id.downloadedradioGroup);
        mRatingBar = findViewById(R.id.ratingBar);

        mProgressBar.setActivated(false);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButton.setAlpha(0);
                mSaveButton.setActivated(false);
                mProgressBar.setAlpha(1);
                mProgressBar.setActivated(true);

                Feedback newFeedback = new Feedback();
                newFeedback.setShopName(mShopName.getText().toString());
                newFeedback.setOwnerName(mShopOwnerName.getText().toString());
                newFeedback.setShopCategory(mShopCategorySpinner.getSelectedItem().toString());
                newFeedback.setShopOwnerSug(mOwnerSug.getText().toString());
                newFeedback.setUserSug(mUserSug.getText().toString());
                newFeedback.setIsInstalled(
                        mAppDownloadedRadioG.getCheckedRadioButtonId() == R.id.down_yes ? "Yes" : "No"
                );
                newFeedback.setIsRegistered(
                        mRegisteredRadioG.getCheckedRadioButtonId() == R.id.reg_yes ? "Yes" : "No"
                );
                newFeedback.setRatings(mRatingBar.getRating());

                String invalidFields = newFeedback.invalidFields();
                if(!invalidFields.isEmpty()) {
                    Toast.makeText(AddFeedback.this, "Please enter " + invalidFields, Toast.LENGTH_SHORT).show();
                    return;
                }

//                newFeedback.save(new Callback() {
//                    @Override
//                    public void onSuccess(Object object) {
//                        Toast.makeText(AddFeedback.this, "Feedback saved", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(Object object) {
//                        Toast.makeText(AddFeedback.this, "Check network connection and try again", Toast.LENGTH_SHORT).show();
//                        mProgressBar.setAlpha(0);
//                        mProgressBar.setActivated(false);
//                        mSaveButton.setActivated(true);
//                        mSaveButton.setAlpha(1);
//                    }
//                });
            }
        });
    }
}