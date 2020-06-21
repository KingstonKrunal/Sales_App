package com.letsbiz.salesapp.Controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.letsbiz.salesapp.Model.Feedback;
import com.letsbiz.salesapp.Model.Callback;
import com.letsbiz.salesapp.Model.FeedbackRepository;
import com.letsbiz.salesapp.R;

import org.jetbrains.annotations.NotNull;

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
                showProgress();

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
                    hideProgress();
                    return;
                }

                FeedbackRepository repository = new FeedbackRepository();
                repository.createFeedback(newFeedback, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        finish();
                    }

                    @Override
                    public void onError(Object object) {
                        Toast.makeText(AddFeedback.this, "Something Unexpected occurred", Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }
                });

            }
        });

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle("New Feedback");
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