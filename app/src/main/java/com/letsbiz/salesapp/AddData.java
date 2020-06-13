package com.letsbiz.salesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity {
    EditText mShopName, mShopOwnerName, mTheirSugg, mYourSugg;
    Spinner mShopCategorySpinner, mDownloadQueSpinner, mRegQueSpinner, mRateQueSpinner;
    Button mSaveButton;
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
        mTheirSugg = findViewById(R.id.theirSuggEditText);
        mYourSugg = findViewById(R.id.yourSuggEditText);
        mDownloadQueSpinner = findViewById(R.id.downloadQueSpinner);
        mRegQueSpinner = findViewById(R.id.regSpinner);
        mRateQueSpinner = findViewById(R.id.rateSpinner);
        mSaveButton = findViewById(R.id.saveButton);
        mProgressBar = findViewById(R.id.progressBar);

        mProgressBar.setActivated(false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButton.setAlpha(0);
                mSaveButton.setActivated(false);
                mProgressBar.setAlpha(1);
                mProgressBar.setActivated(true);

                String err = "";
                Entry  newEntry = new Entry();
                newEntry.setShopName(mShopName.getText().toString());
                newEntry.setOwnerName(mShopOwnerName.getText().toString());
                newEntry.setShopCategory(mShopCategorySpinner.getSelectedItem().toString());
                newEntry.setShopOwnerSug(mTheirSugg.getText().toString());
                newEntry.setUserSug(mYourSugg.getText().toString());
                newEntry.setIsInstalled(mDownloadQueSpinner.getSelectedItem().toString());
                newEntry.setIsRegistered(mRegQueSpinner.getSelectedItem().toString());
                newEntry.setIsRated(mRateQueSpinner.getSelectedItem().toString());

                String invalidFields = newEntry.invalidFields();
                if(!invalidFields.isEmpty()) {
                    Toast.makeText(AddData.this, "Please enter " + invalidFields, Toast.LENGTH_SHORT).show();
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