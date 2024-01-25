package com.alexisboiz.boursewatcher.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.NewsModel.News
import com.squareup.picasso.Picasso

class NewsAdapter(val newsList : List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val thumnail : ImageView = item.findViewById(R.id.thumbnail)
        val article_title : TextView = item.findViewById(R.id.article_title)
        val article_description : TextView = item.findViewById(R.id.article_description)
        val element = item.findViewById<ConstraintLayout>(R.id.element)
        val context = item.context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)

        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.element.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsList[position].url))
            startActivity(holder.item.context, intent, null)
        }
        holder.article_title.text = newsList[position].headline
        holder.article_description.text = newsList[position].summary
        if(newsList[position].image != null){
            Picasso.get().load(newsList[position].image).into(holder.thumnail)
        }
    }
}
