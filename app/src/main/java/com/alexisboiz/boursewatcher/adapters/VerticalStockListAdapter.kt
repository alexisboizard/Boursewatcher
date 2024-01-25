package com.alexisboiz.boursewatcher.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.alexisboiz.boursewatcher.views.market_fragment.StockDetailFragment
import com.bumptech.glide.Glide
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartStackingType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.roundToInt


class VerticalStockListAdapter(val listStock : MutableList<RecyclerHorizontalCard>, val imageUriList: MutableList<String>)
    : RecyclerView.Adapter<VerticalStockListAdapter.ViewHolder>() {
    companion object{
        var closeValue : ArrayList<Double>? = arrayListOf()
        var openValue : ArrayList<Double>? = arrayListOf()
        var chartValue : ArrayList<Double>? = arrayListOf()
    }
    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item){
        val symbol: TextView =  item.findViewById<TextView>(R.id.company_code)
        val regularMarketPrice: TextView = item.findViewById<TextView>(R.id.stock_price)
        val regularMarketChangePercent: TextView = item.findViewById<TextView>(R.id.percent_evolv)
        val arrow : ImageView = item.findViewById<ImageView>(R.id.evolv_logo)
        val chart: AAChartView = item.findViewById<AAChartView>(R.id.chart)
        val companyLogo = item.findViewById<ImageView>(R.id.company_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.vertical_stock_item, parent, false)
        return ViewHolder(item)
    }
    override fun getItemCount(): Int {
        return listStock.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val display = arrayListOf(
            listStock[position].stock?.chart?.result?.get(0)?.meta?.symbol.toString(),
            (((listStock[position].stock?.chart?.result?.get(0)?.meta?.regularMarketPrice?.times(
                100.0
            ))?.roundToInt() ?: 0) /100.0).toString(),
            listStock[position].stock?.chart?.result?.get(0)?.meta?.exchangeName.toString() + " - " + listStock[position]?.stock?.chart?.result?.get(0)?.meta?.currency.toString(),
            listStock[position].stock?.chart?.result?.get(0)?.meta?.previousClose.toString(),
        )
        holder.item.setOnClickListener(){
            val stockDetailFragment = StockDetailFragment()
            closeValue = listStock[position].stock?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.close
            openValue = listStock[position].stock?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
            chartValue = listStock[position].chartData

            stockDetailFragment.arguments = Bundle().apply {
                putStringArrayList("displayInfo", display)
                putIntegerArrayList("volumeValue", listStock[position].stock?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.volume)
            }
            val activity = holder.item.context as AppCompatActivity
            activity.supportFragmentManager.let { stockDetailFragment.show(it,"StockDetailFragment") }
        }
        Log.e("VerticalStockListAdapter", "onBindViewHolder: $listStock")
        val meta = listStock[position].stock?.chart?.result?.get(0)?.meta
        val priceList = listStock[position].chartData
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
        holder.regularMarketPrice.text = meta?.regularMarketPrice.toString() + currency

        val regularMarketPrice = (meta?.regularMarketPrice!! * 100.0).roundToInt() /100.0
        val previousClosePrice = meta?.previousClose
        val regularMarketChangePercent : Double? =
            ((previousClosePrice?.let { regularMarketPrice?.minus(it) })?.div(regularMarketPrice!!))?.times(100)
        if (regularMarketChangePercent != null) {
            if (regularMarketChangePercent < 0){
                holder.regularMarketChangePercent.setTextColor(ContextCompat.getColor(holder.item.context, R.color.down_color))
                holder.arrow.setImageResource(R.drawable.red_down_arrow)
            }
        }
        holder.regularMarketChangePercent.text = (regularMarketChangePercent?.let { abs(Math.round(it*100.0)/100.0) }).toString()
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

        if(listStock[position].logoUrl != null){
            Glide.with(holder.item.context)
                .load(listStock[position].logoUrl)
                .into(holder.companyLogo)
        }
    }
}

