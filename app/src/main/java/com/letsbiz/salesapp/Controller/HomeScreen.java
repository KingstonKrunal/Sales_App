package com.letsbiz.salesapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Any;
import com.letsbiz.salesapp.Model.Callback;
import com.letsbiz.salesapp.Model.User;
import com.letsbiz.salesapp.Model.UserRepository;
import com.letsbiz.salesapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    Button mLogoutBtn, mViewProfileBtn;
    Button mAddFeedbackBtn, mViewFeedbackListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mAddFeedbackBtn = findViewById(R.id.add_feedback_btn);
        mViewFeedbackListBtn = findViewById(R.id.view_feedback_list_btn);
        mLogoutBtn = findViewById(R.id.log_out_btn);
        mViewProfileBtn = findViewById(R.id.view_profile_btn);

        mAddFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, AddFeedback.class));
            }
        });

        mViewFeedbackListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, FeedbackDetails.class));
            }
        });

        mViewProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, UserProfile.class));
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(HomeScreen.this, LoginActivity.class));
                finish();
            }
        });

        UserRepository ur = new UserRepository();
        ur.readAllUserFeedbackIdsBySingleValueEvent(new Callback() {
            @Override
            public void onSuccess(Object object) {
                DocumentSnapshot ds = (DocumentSnapshot) object;
                Toast.makeText(HomeScreen.this, ds.get("feedbackIDs").toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Object object) {
                Exception ex = (Exception) object;
                Toast.makeText(HomeScreen.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(ex.getStackTrace());
            }
        });

        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> ar = new ArrayList<>();
        ar.add("new Arr");
        ar.add("adasdad");
        ar.add("asdadasd");
        map.put("feedbackids", ar);

        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomeScreen.this, task.isCanceled() ? "error" : "successfull", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

