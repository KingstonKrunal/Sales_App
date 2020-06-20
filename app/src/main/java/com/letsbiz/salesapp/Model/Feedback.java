package com.letsbiz.salesapp.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class Feedback {
    private String shopName;
    private String ownerName;
    private String shopCategory;
    private String shopOwnerSug;
    private String userSug;
    private String isInstalled;
    private String isRegistered;
    private String uid;
    private float ratings;
    @ServerTimestamp private long timestamp;

    public Feedback() {
    }

    public String getShopName() {
        return shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public String getShopOwnerSug() {
        return shopOwnerSug;
    }

    public String getUserSug() {
        return userSug;
    }

    public String getIsInstalled() {
        return isInstalled;
    }

    public String getIsRegistered() {
        return isRegistered;
    }

    public float getRatings() {
        return ratings;
    }

    public String getUid() {
        return uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getFormattedDate() {
        Date date = new Date(timestamp);

        String res = "";
        res += DateFormat.getDateInstance(DateFormat.MEDIUM).format(date) + " ";
        res += DateFormat.getTimeInstance(DateFormat.SHORT).format(date);

        return res;
    }

    public String invalidFields() {
        String res = "";
        if (shopName == null || shopName.isEmpty()) res = "Shop name";
        if (ownerName == null || ownerName.isEmpty())
            res += (res.isEmpty() ? "" : " and ") + "Owner name";

        return res;
    }
//
//    public void save(final Callback helper) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser == null) {
//            return;
//        }
//
//        uid = firebaseUser.getUid();
//
//        timestamp = System.currentTimeMillis()/1000;
//
//        final String key = mDatabase.child("feedback").push().getKey();
//        mDatabase.child("feedback").child(key).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                mDatabase.child("users")
//                        .child(uid)
//                        .child("noOfFeedback")
//                        .addListenerForSingleValueEvent(
//                                new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        mDatabase.child("users")
//                                                .child(uid)
//                                                .child("noOfFeedback")
//                                                .setValue((int) dataSnapshot.getValue() + 1);
//
//                                        mDatabase.child("users")
//                                                .child(uid)
//                                                .child("feedbackIds")
//                                                .child(key)
//                                                .setValue(true);
//
//                                        helper.onSuccess(null);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                                        //TODO: Save it offline
//                                    }
//                                }
//                        );
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                helper.onError(null);
//            }
//        });
//    }
}

