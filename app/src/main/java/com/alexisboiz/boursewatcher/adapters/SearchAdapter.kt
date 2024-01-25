package com.alexisboiz.boursewatcher.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SearchAdapter(val itemList : List<Quotes>, val stockDetail : List<RecyclerHorizontalCard>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val favoriteButton : ImageButton = item.findViewById(R.id.favorite_button)
        val name : TextView = item.findViewById(R.id.search_company_name)
        val symbole : TextView = item.findViewById(R.id.search_company_code)
        val stockPrice : TextView = item.findViewById(R.id.search_stock_price)
        val stockEvo : TextView = item.findViewById(R.id.search_stock_percent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.search_bar_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].longname
        holder.symbole.text = itemList[position].symbol
        //holder.stockPrice.text = stockDetail[position].stock?.chart?.result?.get(0)?.meta?.regularMarketPrice.toString()
        val favRef = Firebase.firestore.collection("users/${MainActivity.userId}/favorites")
        val favList = mutableListOf<String>()
        favRef.get().addOnSuccessListener { result ->
            for (document in result) {
                favList.add(document["symbol"].toString())
            }
        }
        Log.e("SearchAdapter", "onBindViewHolder: $favList")
        if(favList.contains(itemList[position].symbol)){
            holder.favoriteButton.setImageResource(R.drawable.favorite_selected)
        }

    }
}
