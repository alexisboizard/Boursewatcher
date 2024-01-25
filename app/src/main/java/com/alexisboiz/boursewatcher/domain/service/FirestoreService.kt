package com.alexisboiz.boursewatcher.domain.service

import android.util.Log
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import com.alexisboiz.boursewatcher.utils.UserError
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirestoreService(private var auth: FirebaseAuth, private var firestore: FirebaseFirestore) {
    lateinit var userResponse : UserDetail

    fun buildForNewUser(userId : String, displayName : String, avatar : String) : Flow<UserDetail> = flow{
        val friendCode = userId.substring(0, 6).uppercase()
        firestore.collection("users").document(userId).set(
            hashMapOf(
                "displayName" to displayName,
                "avatar" to avatar,
                "friendCode" to friendCode,
                "token" to "",
            )
        ).addOnFailureListener{
            Log.e("FirestoreService", "buildForNewUser: ${it.message}")
            userResponse.errors.add(UserError.TRY_AGAIN_LATER)
        }
        emit(userResponse)
    }

}