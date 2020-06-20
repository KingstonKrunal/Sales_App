package com.letsbiz.salesapp.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class FeedbackRepository {
//    private static DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.FEEDBACK);
//
//    public void createFeedback(Feedback feedback, final Callback callback) {
//        String key = feedbackRef.push().getKey();
//        if(feedback != null && !Utility.isEmptyOrNull(key)) {
//            feedback.setUid(User.getUID());
//            feedbackRef.child(key).setValue(feedback)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            callback.onSuccess(null);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            callback.onError(null);
//                        }
//                    });
//        }
//    }
    public void deleteFeedback(String key, Callback callback) {}

    public void updateFeedback(String key, Callback callback) {}

    public void readAllFeedbackBySingleValueEvent(Callback callBack){};

    public void readEmployeeByKey(String employeeKey, final Callback callBack) {
    }
    public FirebaseRequestModel readAllFeedbackByDataChangeEvent(Callback callBack) {
        return null;
    };
//    public FirebaseRequestModel readAllEmployeesByChildEvent(final ChildEventListener firebaseChildCallBack) {
//        return null;
//    }
}

