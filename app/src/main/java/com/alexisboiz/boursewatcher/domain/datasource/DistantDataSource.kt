package com.alexisboiz.boursewatcher.domain.datasource

import android.widget.ImageView
import com.alexisboiz.boursewatcher.domain.service.FirebaseService
import com.alexisboiz.boursewatcher.domain.service.FirestoreService
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import com.alexisboiz.boursewatcher.utils.UserError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DistantDataSource {
    var auth: FirebaseAuth
    var firebaseStorage: FirebaseStorage
    var userResponse: UserDetail
    private var firestore: FirebaseFirestore
    init {
        auth = Firebase.auth
        firestore = Firebase.firestore
        firebaseStorage = Firebase.storage
        userResponse = UserDetail(null, mutableListOf())
    }
    val firebaseService: FirebaseService = FirebaseService(auth, firebaseStorage, userResponse)
}