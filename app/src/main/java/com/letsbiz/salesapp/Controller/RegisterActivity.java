package com.letsbiz.salesapp.Controller;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.letsbiz.salesapp.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText mInputEmail, mInputPassword, mInputUsername;
    Button mRegister, mSignInLinkBtn;
    String mEmail, mPassword, mUserName;
    FirebaseAuth mFirebaseAuth;
    Boolean mEditTextStatus, mCheckPassword;
    ImageButton mShowPassword;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mInputEmail = findViewById(R.id.register_email);
        mInputPassword = findViewById(R.id.register_password);
        mInputUsername = findViewById(R.id.register_username);
        mRegister = findViewById(R.id.btnRegister);
        mSignInLinkBtn = findViewById(R.id.btnLinkSignIN);
        mShowPassword = findViewById(R.id.showPassword);

        //get Firebase auth Instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        //////////////// Show Password(Visibility) /////////////////////
        mShowPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mShowPassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        mInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;

                    case MotionEvent.ACTION_UP:
                        mInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mShowPassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;
                }
                return false;
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling method to check EditText is empty or not
                isValid();

                //if EditText is true
                if (mEditTextStatus) {
                    if (mCheckPassword) {
                        UserRegistrationFunction();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        mSignInLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void UserRegistrationFunction() {
        mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String emailInfo = mInputEmail.getText().toString();
                    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

                    if(fUser != null) {
                        String uId = fUser.getUid();

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(uId).child("name").setValue(mUserName);
                        mDatabase.child("users").child(uId).child("totalEntries").setValue(0);

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().
                                setDisplayName(mUserName).
                                build();

                        fUser.updateProfile(profileUpdate);

                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Email", emailInfo);
                        editor.apply();

                        startActivity(new Intent(RegisterActivity.this, HomeScreen.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                    }
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
        mEmail = mInputEmail.getText().toString().trim();
        mPassword = mInputPassword.getText().toString().trim();
        mUserName = mInputUsername.getText().toString().trim();

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mUserName)) {
            mEditTextStatus = false;
        } else {
            mEditTextStatus = true;
        }

        if (!TextUtils.isEmpty(mPassword)) {
            if (mPassword.length() <= 8) {
                mCheckPassword = false;
                Toast.makeText(RegisterActivity.this, "Password too short, enter minimum 6 character", Toast.LENGTH_LONG).show();
            } else {
                mCheckPassword = true;
            }
        }
    }
}