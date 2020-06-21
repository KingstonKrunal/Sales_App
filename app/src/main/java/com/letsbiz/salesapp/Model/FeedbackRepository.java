package com.letsbiz.salesapp.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackRepository {
    private static CollectionReference feedbackRef = FirebaseFirestore.getInstance()
            .collection(FirebaseConstants.FEEDBACK);

    public void createFeedback(Feedback feedback, final Callback callback) {
        final String key = feedbackRef.document().getId();
        if(feedback != null && !Utility.isEmptyOrNull(key)) {

            feedback.setUid(User.getUID());

            feedbackRef.document(key).set(feedback)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                callback.onSuccess(null);
                            } else {
                                callback.onError(null);
                            }
                        }
                    });
        }
    }

    public void deleteFeedback(final String key, final Callback callback) {
        feedbackRef.document(key).delete()
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

    public void updateFeedback(String key, Feedback feedback, final Callback callback) {
        feedbackRef.document(key).update(feedback.updatedFeedbackMap())
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

