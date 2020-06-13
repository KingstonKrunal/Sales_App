package com.letsbiz.salesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText shopName, shopOwnerName, theirSugg, yourSugg;
    Spinner shopCategorySpinner, downloadQueSpinner, regQueSpinner, rateQueSpinner;
    Button saveButton;
    private DatabaseReference mDatabase;

    String[] shopCategoryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        shopCategoryArray = getResources().getStringArray(R.array.shopCategory);

//        List shopCategoryList = new ArrayList(shopCategoryArray.length);
//
//        for (String s:shopCategoryArray) {
//            shopCategoryList.add(s);
//        }
//
//        Collections.sort(shopCategoryList);

        shopName = findViewById(R.id.shopNameEditText);
        shopOwnerName = findViewById(R.id.shopOwnerNameEdittext);
        shopCategorySpinner = findViewById(R.id.shopCategorySpinner);
        theirSugg = findViewById(R.id.theirSuggEditText);
        yourSugg = findViewById(R.id.yourSuggEditText);
        downloadQueSpinner = findViewById(R.id.downloadQueSpinner);
        regQueSpinner = findViewById(R.id.regSpinner);
        rateQueSpinner = findViewById(R.id.rateSpinner);
        saveButton = findViewById(R.id.saveButton);

        shopCategorySpinner.setOnItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddData.this, HomeScreen.class));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String categoryData = (String) shopCategorySpinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}