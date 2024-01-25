package com.alexisboiz.boursewatcher.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class FriendRequestAdapter(val nameList : MutableMap<String,String>, val userId : String) : RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val displayName: TextView = item.findViewById(R.id.tv_name)
        val checkButton : ImageButton = item.findViewById(R.id.btn_accept)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.friend_request_item, parent, false)

        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val displayName = nameList.keys.elementAt(position)
        val friendId = nameList.values.elementAt(position)
        val userId = Firebase.auth.currentUser?.uid
        holder.displayName.text = displayName
        holder.checkButton.setOnClickListener {
            Firebase.firestore.collection("users/$userId/friendList").get()
                .addOnSuccessListener {
                    for (document in it){
                        Log.e("userId", userId.toString())
                        if(friendId == document.data["friendId"]){
                            document.reference.update("status", "accepted")
                        }
                    }
                }
            Firebase.firestore.collection("users/$friendId/friendList").get()
                .addOnSuccessListener {
                    for (document in it){
                        if(userId == document.data["friendId"]){
                            document.reference.update("status", "accepted")
                        }
                    }
                }
            nameList.remove(displayName, friendId)
            notifyItemRemoved(position)
        }
        /*if (mapImageUriDisplayName.values.elementAt(position) != null) {
            Glide
                .with(holder.profil_picture.context)
                .load(mapImageUriDisplayName.keys.elementAt(position))
                .into(holder.profil_picture)
        }*/
    }
}
