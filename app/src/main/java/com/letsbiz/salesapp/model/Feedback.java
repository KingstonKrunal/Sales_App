package com.letsbiz.salesapp.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feedback implements Serializable {
    private String shopName;
    private String ownerName;
    private String shopCategory;
    private String shopOwnerSug;
    private String userSug;
    private String isInstalled;
    private String isRegistered;
    private String uid;
    private float ratings;
    @ServerTimestamp private Date date;

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

    public Date getDate() {
        return date;
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

    public Map<String, Object> updatedFeedbackMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopName", shopName);
        map.put("ownerName", ownerName);
        map.put("shopCategory", shopCategory);
        map.put("shopOwnerSug", shopOwnerSug);
        map.put("userSug", userSug);
        map.put("isInstalled", isInstalled);
        map.put("isRegistered", isRegistered);
        map.put("ratings", ratings);

        return map;
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

