package com.alexisboiz.boursewatcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.StockRepository
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel
import kotlin.math.roundToInt

class SearchAdapter(val searchList : ArrayList<Quotes>, val stockInfo: MutableList<Meta>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder (val item : View) : RecyclerView.ViewHolder(item){
        val favoriteButton = item.findViewById<ImageButton>(R.id.favorite_button)
        val companyName = item.findViewById<TextView>(R.id.search_company_name)
        val companyCode = item.findViewById<TextView>(R.id.search_company_code)
        val stockPrice = item.findViewById<TextView>(R.id.search_stock_price)
        val stockPercentEvolv = item.findViewById<TextView>(R.id.search_stock_percent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_stock_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return stockInfo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currency = ""
        when (stockInfo[position].currency){
            "USD" -> currency = "$"
            "EUR" -> currency = "€"
            "GBP" -> currency = "£"
            "CAD" -> currency = "C$"
            "AUD" -> currency = "A$"
            "JPY" -> currency = "¥"
            "CNY" -> currency = "¥"
            "INR" -> currency = "₹"
            "HKD" -> currency = "HK$"
            "CHF" -> currency = "CHF"
            "TWD" -> currency = "NT$"
            "SEK" -> currency = "kr"
            "KRW" -> currency = "₩"
            "SGD" -> currency = "S$"
            "NOK" -> currency = "kr"
            "MXN" -> currency = "Mex$"
            "NZD" -> currency = "NZ$"

        }

        holder.companyName.text = searchList[position].longname
        holder.companyCode.text = searchList[position].symbol
        //holder.stockPrice.text = stockInfo[position].regularMarketPrice.toString() + currency

        //val regularMarketPrice = (stockInfo[position].regularMarketPrice!! * 100.0).roundToInt() /100.0
        //val previousClosePrice = stockInfo[position].previousClose
        //val regularMarketChangePercent : Double? =
        //    ((previousClosePrice?.let { regularMarketPrice.minus(it) })?.div(regularMarketPrice!!))?.times(100)
        //holder.stockPercentEvolv.text = regularMarketChangePercent.toString() + "%"

//        if (regularMarketChangePercent != null) {
//            if (regularMarketChangePercent > 0) {
//                holder.stockPercentEvolv.setTextColor(
//                    ContextCompat.getColor(
//                        holder.item.context,
//                        R.color.down_color
//                    )
//                )
//            }
//        }
    }

}
