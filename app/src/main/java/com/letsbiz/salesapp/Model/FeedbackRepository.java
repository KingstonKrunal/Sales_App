package com.letsbiz.salesapp.Model;

import android.telecom.Call;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class FeedbackRepository {
    private FirebaseUser user;

    public FeedbackRepository(FirebaseUser user) {
       this.user = user;
    }

    private static CollectionReference feedbackRef = FirebaseFirestore.getInstance()
            .collection(FirebaseConstants.FEEDBACK);

    public void createFeedback(Feedback feedback, final Callback callback) {
        final String key = feedbackRef.document().getId();
        if(feedback != null && !Utility.isEmptyOrNull(key)) {

            feedback.setUid(user.getUid());

            feedbackRef.document(key).set(feedback)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callback.onSuccess(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(null);
                        }
                    });
        }
    }

    public void deleteFeedback(final String key, final Callback callback) {
        feedbackRef.document(key).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onSuccess(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(null);
                    }
                });
    }

    public void updateFeedback(String key, Feedback feedback, final Callback callback) {
        feedbackRef.document(key).update(feedback.getMap())
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    callback.onSuccess(null);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onError(null);
                }
            });
    }

    public void readAllFeedback(final Callback callBack){
        feedbackRef.whereEqualTo("uid", user.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        callBack.onSuccess(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    };
}

