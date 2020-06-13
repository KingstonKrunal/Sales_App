package com.letsbiz.salesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword, inputUsername;
    Button Register, SignInLinkBtn;
    String Email, Password, userName;
    FirebaseAuth firebaseAuth;
    Boolean EditTextStatus, checkPassword;
    ImageButton showPassword;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.register_email);
        inputPassword = findViewById(R.id.register_password);
        inputUsername = findViewById(R.id.register_username);
        Register = findViewById(R.id.btnRegister);
        SignInLinkBtn = findViewById(R.id.btnLinkSignIN);
        showPassword = findViewById(R.id.showPassword);

        //get Firebase auth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        //////////////// Show Password(Visibility) /////////////////////
        showPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showPassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;

                    case MotionEvent.ACTION_UP:
                        inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showPassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;
                }
                return false;
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling method to check EditText is empty or not
                isValid();

                //if EditText is true
                if (EditTextStatus) {
                    if (checkPassword) {
                        UserRegistrationFunction();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        SignInLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void UserRegistrationFunction() {
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String emailInfo = inputEmail.getText().toString();
                    final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").child(uId).child("name").setValue(userName);

                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email", emailInfo);
                    editor.apply();

                    startActivity(new Intent(RegisterActivity.this, HomeScreen.class));
                    finish();
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(RegisterActivity.this, "Weak Password", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(RegisterActivity.this, "Invalid Data", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(RegisterActivity.this, "User exist", Toast.LENGTH_LONG).show();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(RegisterActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    } catch (Exception ignored) {

                    }
                }
            }
        });
    }

    public void isValid() {
        Email = inputEmail.getText().toString().trim();
        Password = inputPassword.getText().toString().trim();
        userName = inputUsername.getText().toString().trim();

        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(userName)) {
            EditTextStatus = false;
        } else {
            EditTextStatus = true;
        }

        if (!TextUtils.isEmpty(Password)) {
            if (Password.length() <= 8) {
                checkPassword = false;
                Toast.makeText(RegisterActivity.this, "Password too short, enter minimum 6 character", Toast.LENGTH_LONG).show();
            } else {
                checkPassword = true;
            }
        }
    }
}