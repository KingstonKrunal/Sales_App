package com.letsbiz.salesapp.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class Feedback {
    public String shopName;
    public String ownerName;
    public String shopCategory;
    public String shopOwnerSug;
    public String userSug;
    public String isInstalled;
    public String isRegistered;
    public float ratings;
    public String uid;

    public Feedback() {
    }

//    public Feedback(String shopName, String ownerName, String shopCategory, String shopOwnerSug, String userSug, String isInstalled, String isRegistered, float ratings) {
//        this.shopName = shopName;
//        this.ownerName = ownerName;
//        this.shopCategory = shopCategory;
//        this.shopOwnerSug = shopOwnerSug.isEmpty() ? "None" : shopOwnerSug;
//        this.userSug = userSug.isEmpty() ? "None" : userSug;
//        this.isInstalled = isInstalled;
//        this.isRegistered = isRegistered;
//        this.ratings = ratings;
//    }


    public String invalidFields() {
        String res = "";
        if (shopName == null || shopName.isEmpty()) res = "Shop name";
        if (ownerName == null || ownerName.isEmpty())
            res += (res.isEmpty() ? "" : " and ") + "Owner name";

        return res;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public void setShopOwnerSug(String shopOwnerSug) {
        this.shopOwnerSug = shopOwnerSug;
    }

    public void setUserSug(String userSug) {
        this.userSug = userSug;
    }

    public void setIsInstalled(String isInstalled) {
        this.isInstalled = isInstalled;
    }

    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public void save(final FeedbackSaveListener helper) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            return;
        }

        uid = firebaseUser.getUid();

        final String key = mDatabase.child("feedback").push().getKey();
        mDatabase.child("feedback").child(key).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDatabase.child("users")
                        .child(uid)
                        .child("noOfFeedback")
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        mDatabase.child("users")
                                                .child(uid)
                                                .child("noOfFeedback")
                                                .setValue((int) dataSnapshot.getValue() + 1);

                                        mDatabase.child("users")
                                                .child(uid)
                                                .child("feedbackIds")
                                                .child(key)
                                                .setValue(true);

                                        helper.onSaveComplete();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        //TODO: Save it offline
                                    }
                                }
                        );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                helper.onSaveFailure();
            }
        });
    }
}

