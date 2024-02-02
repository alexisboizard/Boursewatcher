package com.alexisboiz.boursewatcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.bumptech.glide.Glide
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartStackingType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlin.math.roundToInt

class FavoriteListAdapter(
    val favoriteList : MutableList<RecyclerHorizontalCard>,
) : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item){
        val symbol: TextView =  item.findViewById<TextView>(R.id.company_code)
        val regularMarketPrice: TextView = item.findViewById<TextView>(R.id.stock_price)
        val regularMarketChangePercent: TextView = item.findViewById<TextView>(R.id.percent_evolv_horizontal)
        val arrow : ImageView = item.findViewById<ImageView>(R.id.evolv_logo)
        val chart: AAChartView = item.findViewById<AAChartView>(R.id.chart)
        val company_logo: ImageView = item.findViewById<ImageView>(R.id.company_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListAdapter.ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_stock_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meta = favoriteList[position].stock?.data?.chart?.result?.get(0)?.meta
        val priceList = favoriteList[position].chartData
        var currency = ""
        when (meta?.currency){
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
        holder.symbol.text = meta?.symbol
        val regularMarketPrice = ((meta?.regularMarketPrice!! * 100.0).roundToInt() /100.0)
        holder.regularMarketPrice.text = regularMarketPrice.toString() + currency
        // TODO: 2021-08-31  fix this

        val chartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Spline)
            .backgroundColor("#ffffff")
            .dataLabelsEnabled(false)
            .xAxisVisible(false)
            .yAxisVisible(false)
            .legendEnabled(false)
            .tooltipEnabled(false)
            .touchEventEnabled(false)
            .animationType(AAChartAnimationType.EaseOutQuart)
            .animationDuration(1200)
            .colorsTheme(arrayOf("#1f1f1f"))
            .stacking(AAChartStackingType.False)
            .markerRadius(0)
            .margin(arrayOf(20, 20, 20, 20))
            .series(arrayOf(
                AASeriesElement()
                    .name("")
                    .data(priceList.toTypedArray())
            ))
        holder.chart.aa_drawChartWithChartModel(chartModel)
        if(favoriteList[position].stock?.logo?.get(0)?.image != "null"){
            Glide.with(holder.item.context)
                .load(favoriteList[position].stock?.logo?.get(0)?.image)
                .into(holder.company_logo)
        }
    }
}