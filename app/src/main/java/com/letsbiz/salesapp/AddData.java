package com.letsbiz.salesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText mShopName, mShopOwnerName, mTheirSugg, mYourSugg;
    Spinner mShopCategorySpinner, mDownloadQueSpinner, mRegQueSpinner, mRateQueSpinner;
    Button mSaveButton;
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

        mShopCategorySpinner.setOnItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String invalidFileds = newEntry.invalidFilds();
                if(!invalidFileds.isEmpty()) {
                    Toast.makeText(AddData.this, "Please enter " + invalidFileds, Toast.LENGTH_SHORT).show();
                }

                mDatabase.child("entries").push().setValue(newEntry);

                startActivity(new Intent(AddData.this, HomeScreen.class));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String categoryData = (String) shopCategorySpinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}