package com.letsbiz.salesapp.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.letsbiz.salesapp.model.Callback;
import com.letsbiz.salesapp.model.User;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.model.UserRepository;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText emailId, password;
    Button signIn, createAccount;
    FirebaseAuth firebaseAuth;
    ImageButton showPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showPassword = findViewById(R.id.showPassword);
        emailId = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.sign_in_button);
        createAccount = findViewById(R.id.sign_in_create_account);

        FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();

                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, FeedbackListActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        showPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showPassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;

                    case MotionEvent.ACTION_UP:
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showPassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailInfo = emailId.getText().toString();
                final String passwordInfo = password.getText().toString();

                firebaseAuth = FirebaseAuth.getInstance();

                if (emailInfo.isEmpty()) {
                    emailId.setError("Please enter your Email ID.");
                    emailId.requestFocus();
                } else if (passwordInfo.isEmpty()) {
                    password.setError("Please enter your Password");
                    password.requestFocus();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(emailInfo, passwordInfo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // saveInfo
//                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("Email", emailInfo);
//                                editor.putString("Password", String.valueOf(password));
//                                editor.apply();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user == null) return;

                                new UserRepository(user.getUid()).checkIfAdmin(new Callback() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        boolean isAdmin = (boolean) object;
                                        Intent i = new Intent(LoginActivity.this, FeedbackListActivity.class);

                                        if(isAdmin) {
                                            i.putExtra("isAdmin", true);
                                        }

                                        startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Object object) {
                                        Toast.makeText(LoginActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                                startActivity(new Intent(LoginActivity.this, FeedbackListActivity.class));
                                finish();
                            }
                            else {
                                try {
                                    throw Objects.requireNonNull(task.getException());
                                }catch (FirebaseAuthWeakPasswordException e){
                                    Toast.makeText(LoginActivity.this, "Weak Password" , Toast.LENGTH_LONG).show();
                                }catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e){
                                    Toast.makeText(LoginActivity.this, "Invalid Data" , Toast.LENGTH_LONG).show();
                                }catch (FirebaseAuthUserCollisionException e){
                                    Toast.makeText(LoginActivity.this, "User exist" , Toast.LENGTH_LONG).show();
                                } catch (FirebaseNetworkException e){
                                    Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                }catch (Exception e){
                                    Toast.makeText(LoginActivity.this, "Failed" + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}