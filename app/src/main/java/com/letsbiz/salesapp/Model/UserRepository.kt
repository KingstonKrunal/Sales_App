package com.letsbiz.salesapp.Model

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val FEEDBACK_COUNT = "feedbackCount"
    val FEEDBACK_REFS = "feedbackids"
    private val userRef = FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
            .document("userui1")

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
            }
        }
        .addOnFailureListener {
            callback?.onError(it)
        }
    }


}