package com.alexisboiz.boursewatcher.views.friends_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.FriendRequestAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class FriendRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_request)
        val firestore = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val mapNameId = mutableMapOf<String,String>()
        val recyclerView = findViewById<RecyclerView>(R.id.rv_friend_requests)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FriendRequestActivity)
            adapter = adapter
        }


        val docRef = firestore.collection("users/${uid}/friendList").get()
            .addOnSuccessListener{
                for (document in it){
                    if(document.data["status"] == "pending"){
                        Firebase.firestore.collection("users").document(document.data["friendId"].toString()).get()
                            .addOnSuccessListener {
                                mapNameId.put(it.data?.get("displayName").toString(), document.data["friendId"].toString())
                                recyclerView.adapter = FriendRequestAdapter(mapNameId, uid!!)
                            }
                            .addOnFailureListener {
                                println("Error getting documents: $it")
                            }
                    }
                }
            }


        }
}