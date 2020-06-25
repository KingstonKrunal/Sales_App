package com.letsbiz.salesapp.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PropertyName

class UserRepository(private val mUid: String = "") {
    private val userRef = FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)

    fun addNewUser(user: User, callback: Callback) {
        userRef.document(mUid).set(user)
                .addOnSuccessListener {
                    callback.onSuccess(null)
                }
                .addOnFailureListener {
                    callback.onError(it)
                }
    }

    fun fetchUserList(callback: Callback) {
        userRef.get()
                .addOnSuccessListener {
                    callback.onSuccess(it)
                }
                .addOnFailureListener {
                    callback.onError(it)
                }
    }

    fun checkIfAdmin(callback: Callback) {
        val db = FirebaseFirestore.getInstance()
        db.collection("app").document("app1")
                .get()
                .addOnSuccessListener(OnSuccessListener { documentSnapshot ->
                    val adminDoc = documentSnapshot.toObject(AdminDocument::class.java)
                    adminDoc?.adminUids?.forEach { key ->
                        if (key == mUid) {
                            // TODO: Add that data persistence
                            callback.onSuccess(true)
                            return@OnSuccessListener
                        }
                    }
                    // TODO: Add that data persistence
                    callback.onSuccess(false)
                })
                .addOnFailureListener { callback.onError(null) }
    }

    internal class AdminDocument {
        @PropertyName("admins")
        lateinit var adminUids: List<String>
    }
}