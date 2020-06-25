package com.letsbiz.salesapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.PropertyName;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.model.Callback;
import com.letsbiz.salesapp.model.User;
import com.letsbiz.salesapp.model.UserRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String mUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread splashThread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        mUid = user.getUid();

                        new UserRepository(mUid).checkIfAdmin(new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                boolean isAdmin = (boolean) object;
                                Intent i = new Intent(MainActivity.this, FeedbackListActivity.class);

                                if(isAdmin) {
                                    i.putExtra("isAdmin", true);
                                }

                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onError(Object object) {
                                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        };

        splashThread.start();
    }
}