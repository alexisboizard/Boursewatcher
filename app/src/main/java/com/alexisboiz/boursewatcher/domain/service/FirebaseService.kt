package com.alexisboiz.boursewatcher.domain.service

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import com.alexisboiz.boursewatcher.utils.UserError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream

class FirebaseService {
    private var auth : FirebaseAuth
    private var firebaseStorage : FirebaseStorage
    private var userResponse : UserDetail
    constructor(
        auth: FirebaseAuth,
        firebaseStorage: FirebaseStorage,
        userResponse: UserDetail
    ){
        this.auth = auth
        this.firebaseStorage = firebaseStorage
        this.userResponse = userResponse
    }
    fun register(email : String, password : String, avatar : ImageView, firstname :String, lastname : String) : Flow<UserDetail> = flow{
        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()){
            userResponse.errors.add(UserError.EMPTY_FIELD)
            emit(userResponse)
            return@flow
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val userRef = DistantDataSource.firebaseStorage.reference.child("users/${user?.uid}/avatar.jpg")

                    val bitmap = avatar.drawable.toBitmap()
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    var uploadTask = userRef.putBytes(data)
                    uploadTask.addOnFailureListener {
                        userResponse.errors.add(UserError.PROFILE_PICTURE_ERROR)
                        return@addOnFailureListener
                    }
                    var profile_pic_uri : Uri = Uri.EMPTY
                    userRef.downloadUrl.addOnSuccessListener {
                        profile_pic_uri = it
                    }
                    val profileUpdates = userProfileChangeRequest {
                        displayName = "$firstname $lastname"
                        photoUri = profile_pic_uri
                    }
                    Firebase.firestore.collection("users").document(user?.uid!!).set(
                        hashMapOf(
                            "displayName" to "$firstname $lastname",
                            "avatar" to profile_pic_uri.toString(),
                            "friendCode" to user.uid.substring(0, 6).uppercase(),
                            "token" to "",
                        )
                    ).addOnFailureListener{
                        Log.e("FirestoreService", "buildForNewUser: ${it.message}")
                        userResponse.errors.add(UserError.TRY_AGAIN_LATER)
                    }

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("RegisterFragment", "User profile updated.")
                            }else{
                                userResponse.errors.add(UserError.TRY_AGAIN_LATER)
                                return@addOnCompleteListener
                            }
                        }
                    userResponse.user = user
                } else {
                    userResponse.errors.add(UserError.UNKNOWN_ERROR)
                }

            }
            .addOnFailureListener {
                userResponse.errors.add(UserError.UNKNOWN_ERROR)
            }
        emit(userResponse)
    }


    fun login(email : String, password : String) : UserDetail {

        this.auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful){
                    userResponse.user = Firebase.auth.currentUser

                }else{
                    userResponse.errors.add(UserError.LOGIN_ERROR)
                }
            }

        return userResponse
    }

    fun getUserAvatar() : StorageReference {
        return firebaseStorage.reference.child("users/${this.auth.currentUser?.uid}/avatar.jpg")
    }
}