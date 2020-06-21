package com.letsbiz.salesapp.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository(val user: FirebaseUser) {
    private val userRef = FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
            .document(user.uid)

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
}