package com.alexisboiz.boursewatcher.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.model.DayGainersModel.Quotes
import com.alexisboiz.boursewatcher.views.market_fragment.StockDetailFragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import kotlin.math.round

class GainerListAdapter(
    val gainerList : MutableList<Quotes>,
    private val imageUriList : MutableList<String>
) : RecyclerView.Adapter<GainerListAdapter.ViewHolder>() {

    companion object{
        var closeValue : ArrayList<Double>? = arrayListOf()
        var openValue : ArrayList<Double>? = arrayListOf()
        var chartValue : ArrayList<Double>? = arrayListOf()
    }

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item){
        val symbol: TextView =  item.findViewById<TextView>(R.id.company_code)
        val regularMarketPrice: TextView = item.findViewById<TextView>(R.id.stock_price)
        val regularMarketChangePercent: TextView = item.findViewById<TextView>(R.id.percent_evolv_horizontal)
        val arrow : ImageView = item.findViewById<ImageView>(R.id.evolv_logo)
        val chart: AAChartView = item.findViewById<AAChartView>(R.id.chart)
        val company_logo: ImageView = item.findViewById<ImageView>(R.id.company_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GainerListAdapter.ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_stock_item, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return gainerList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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