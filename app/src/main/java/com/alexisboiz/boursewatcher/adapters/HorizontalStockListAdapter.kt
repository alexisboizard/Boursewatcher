package com.alexisboiz.boursewatcher.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.StockRepository
import com.alexisboiz.boursewatcher.model.DayGainersModel.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartStackingType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

class HorizontalStockListAdapter(
    val gainerList : MutableList<Quotes>,
    val imageUriList : MutableList<String>
) : RecyclerView.Adapter<HorizontalStockListAdapter.ViewHolder>() {

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item){
        val symbol: TextView =  item.findViewById<TextView>(R.id.company_code)
        val regularMarketPrice: TextView = item.findViewById<TextView>(R.id.stock_price)
        val regularMarketChangePercent: TextView = item.findViewById<TextView>(R.id.percent_evolv_horizontal)
        val arrow : ImageView = item.findViewById<ImageView>(R.id.evolv_logo)
        val chart: AAChartView = item.findViewById<AAChartView>(R.id.chart)
        val company_logo: ImageView = item.findViewById<ImageView>(R.id.company_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalStockListAdapter.ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_stock_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return gainerList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (imageUriList[position] != ""){
            Picasso.get().load(imageUriList[position]).into(holder.company_logo);
        }
        var currency = ""
        when (gainerList[position].currency){
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
        holder.symbol.text = gainerList[position].symbol
        holder.regularMarketPrice.text = gainerList[position].regularMarketPrice.toString() + currency

        if (gainerList[position].regularMarketChangePercent != null) {
            if (gainerList[position].regularMarketChangePercent!! < 0){
                holder.regularMarketChangePercent.setTextColor(ContextCompat.getColor(holder.item.context, R.color.down_color))
                holder.arrow.setImageResource(R.drawable.red_down_arrow)
            }
        }
        holder.regularMarketChangePercent.text = (gainerList[position].regularMarketChangePercent?.let {
            round(
                it
            )
        }).toString() + "%"

//        val chartModel : AAChartModel = AAChartModel()
//            .chartType(AAChartType.Spline)
//            .backgroundColor("#ffffff")
//            .dataLabelsEnabled(false)
//            .xAxisVisible(false)
//            .yAxisVisible(false)
//            .legendEnabled(false)
//            .tooltipEnabled(false)
//            .touchEventEnabled(false)
//            .animationType(AAChartAnimationType.EaseOutQuart)
//            .animationDuration(1200)
//            .colorsTheme(arrayOf("#1f1f1f"))
//            .stacking(AAChartStackingType.False)
//            .markerRadius(0)
//            .margin(arrayOf(20, 20, 20, 20))
//            .series(arrayOf(
//                AASeriesElement()
//                    .name("")
//                    .data(priceList[position].toTypedArray())
//            ))
//        holder.chart.aa_drawChartWithChartModel(chartModel)
    }
}