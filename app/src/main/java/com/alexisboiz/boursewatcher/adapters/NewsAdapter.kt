package com.alexisboiz.boursewatcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.StockRepository
import com.alexisboiz.boursewatcher.model.NewsModel.News
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class NewsAdapter(val newsList : List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val thumnail : ImageView = item.findViewById(R.id.thumbnail)
        val article_title : TextView = item.findViewById(R.id.article_title)
        val article_description : TextView = item.findViewById(R.id.article_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.article_title.text = newsList[position].headline
        holder.article_description.text = newsList[position].summary
        if(newsList[position].image != null){
            Picasso.get().load(newsList[position].image).into(holder.thumnail)
        }
    }
}
