package com.letsbiz.salesapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity {
    EditText mShopName, mShopOwnerName, mOwnerSug, mUserSug;
    Spinner mShopCategorySpinner;
    Button mSaveButton;
    RadioGroup mAppDownloadedRadioG, mRegisteredRadioG;
    RatingBar mRatingBar;
    ProgressBar mProgressBar;
    private DatabaseReference mDatabase;

    String[] shopCategoryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButton.setAlpha(0);
                mSaveButton.setActivated(false);
                mProgressBar.setAlpha(1);
                mProgressBar.setActivated(true);

                Entry  newEntry = new Entry();
                newEntry.setShopName(mShopName.getText().toString());
                newEntry.setOwnerName(mShopOwnerName.getText().toString());
                newEntry.setShopCategory(mShopCategorySpinner.getSelectedItem().toString());
                newEntry.setShopOwnerSug(mOwnerSug.getText().toString());
                newEntry.setUserSug(mUserSug.getText().toString());
                newEntry.setIsInstalled(
                        mAppDownloadedRadioG.getCheckedRadioButtonId() == R.id.down_yes ? "Yes" : "No"
                );
                newEntry.setIsRegistered(
                        mRegisteredRadioG.getCheckedRadioButtonId() == R.id.reg_yes ? "Yes" : "No"
                );
                newEntry.setRatings(mRatingBar.getRating());


                String invalidFields = newEntry.invalidFields();
                if(!invalidFields.isEmpty()) {
                    Toast.makeText(AddData.this, "Please enter " + invalidFields, Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabase.child("entries").push().setValue(newEntry).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddData.this, "Entry done", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddData.this, "Failed", Toast.LENGTH_SHORT).show();
                        mProgressBar.setAlpha(0);
                        mProgressBar.setActivated(false);
                        mSaveButton.setActivated(true);
                        mSaveButton.setAlpha(1);
                    }
                });

            }
        });
    }
}