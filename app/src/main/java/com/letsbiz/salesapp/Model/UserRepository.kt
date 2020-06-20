package com.letsbiz.salesapp.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val FEEDBACK_COUNT = "feedbackCount"
    val FEEDBACK_REFS = "feedbackIDs"
    private val userRef = FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
            .document(User.getUID())

    //    public void createUser(final String name, final String email, final String password, final Callback callback) {
    //        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    //                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
    //                    @Override
    //                    public void onSuccess(AuthResult authResult) {
    //                        if(Utility.isEmptyOrNull(name))
    //                            updateUserName(name, new Callback() {
    //                                @Override
    //                                public void onSuccess() {
    //                                    callback.onSuccess();
    //                                }
    //
    //                                @Override
    //                                public void onError() {
    //                                    callback.onError();
    //                                }
    //                            });
    //                    }
    //                })
    //                .addOnFailureListener(new OnFailureListener() {
    //                    @Override
    //                    public void onFailure(@NonNull Exception e) {
    //                        callback.onError();
    //                    }
    //                });
    //    }
    //
    //    public void login(final String email, final String password) {
    //
    //    }
    fun updateUserName(name: String?, callback: Callback) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
            user.updateProfile(changeRequest).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(null)
                } else {
                    callback.onError(null)
                }
            }
        }
    }

    fun addFeedback(ref: String?, callback: Callback) {
        if (!Utility.isEmptyOrNull(ref)) {
            userRef.update(FEEDBACK_REFS, FieldValue.arrayUnion(ref))
                    .addOnSuccessListener { callback.onSuccess(null) }
                    .addOnFailureListener { callback.onError(null) }
        }
    }

    fun removeFeedback(ref: String?, callback: Callback) {
        if (Utility.isEmptyOrNull(ref)) {
            userRef.update(FEEDBACK_REFS, FieldValue.arrayRemove(ref))
                    .addOnSuccessListener { callback.onSuccess(null) }
                    .addOnFailureListener { callback.onError(null) }
        }
    }

    fun readAllUserFeedbackIdsBySingleValueEvent(callback: Callback?) {
        userRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                callback?.onError(documentSnapshot);
                Log.e("UserFeedbackList", documentSnapshot[FEEDBACK_REFS].toString());
            }
        }
        .addOnFailureListener { }
    }
}