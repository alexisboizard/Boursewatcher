package com.alexisboiz.boursewatcher.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.AuthModel.FirebaseUser
import com.bumptech.glide.Glide

class FriendAddAdapter(val listOfUser : List<FirebaseUser>) : RecyclerView.Adapter<FriendAddAdapter.ViewHolder>() {

    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val profil_picture : ImageView = item.findViewById(R.id.iv_friend)
        val displayName: TextView = item.findViewById<TextView>(R.id.tv_friend_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.add_friends_item, parent, false)

        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.displayName.text = listOfUser.elementAt(position).displayName
        if (listOfUser.elementAt(position).photoUrl != "") {
            Glide
                .with(holder.profil_picture.context)
                .load(listOfUser.elementAt(position).photoUrl)
                .into(holder.profil_picture)
        }else{
            Glide
                .with(holder.profil_picture.context)
                .load(R.drawable.profile_default)
                .into(holder.profil_picture)
        }
    }
}
