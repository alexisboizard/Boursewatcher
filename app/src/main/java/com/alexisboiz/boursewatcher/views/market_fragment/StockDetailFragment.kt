package com.alexisboiz.boursewatcher.views.market_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentStockDetailBinding
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartStackingType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt

class StockDetailFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentStockDetailBinding
    private var listStock: RecyclerHorizontalCard? = null
    private var displayInfo: ArrayList<String>? = null
    private var closeValue: ArrayList<Double>? = null
    private var volumeValue: ArrayList<Int>? = null
    private var openValue: ArrayList<Double>? = null
    private var chartValue: ArrayList<Double>? = null
    private var origine: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            displayInfo = it.getStringArrayList("displayInfo")
            volumeValue = it.getIntegerArrayList("volumeValue")
            origine = it.getChar("origine")
        }

        if(origine == 'V'){
            closeValue = VerticalStockListAdapter.closeValue
            openValue = VerticalStockListAdapter.openValue
            chartValue = VerticalStockListAdapter.chartValue
        }else{
            closeValue = GainerListAdapter.closeValue
            openValue = GainerListAdapter.openValue
            chartValue = GainerListAdapter.chartValue
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStockDetailBinding.inflate(inflater,container,false)
        val close = binding.root.findViewById<CardView>(R.id.close)

        close.setOnClickListener(){
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockSymbol = view.findViewById<TextView>(R.id.stock_symbol)
        val stockName = view.findViewById<TextView>(R.id.stock_name)
        val stockPrice = view.findViewById<TextView>(R.id.stock_price)
        val stockEvolv = view.findViewById<TextView>(R.id.percent_evolv)
        val exchangeName = view.findViewById<TextView>(R.id.exchange_name)
        val openingPrice = view.findViewById<TextView>(R.id.opening_price)
        val minPrice = view.findViewById<TextView>(R.id.min_price)
        val maxPrice = view.findViewById<TextView>(R.id.max_price)
        val volume = view.findViewById<TextView>(R.id.volume)
        val marketCap = view.findViewById<TextView>(R.id.capitalization)
        val rsi = view.findViewById<TextView>(R.id.rsi)

        stockSymbol.text = displayInfo?.get(0)
        stockPrice.text = displayInfo?.get(1)
        exchangeName.text = displayInfo?.get(2)
        openingPrice.text = (((openValue?.get(0)?.times(100.0))?.roundToInt() ?: 0) / 100.0).toString()

        val closeValueClean = arrayListOf<Double>()
        for (close in closeValue!!){
            if (close == null) continue
            closeValueClean.add(close)
        }
        minPrice.text = ((closeValueClean.min()* 100.0).roundToInt() / 100.0).toString()
        maxPrice.text = ((closeValueClean.max()* 100.0).roundToInt() / 100.0).toString()


        var volAvg = 0
        for (vol in volumeValue!!){
            if (vol == null) continue
            volAvg += vol
        }
        volAvg = volAvg.div(volumeValue?.size!!)
        volume.text = volAvg.toString()
        val chartView = view.findViewById<com.github.aachartmodel.aainfographics.aachartcreator.AAChartView>(R.id.chart)
        buildChart(chartValue!!,chartView)

        //val evolv = (closeValue?.get(0)?.minus(openValue?.get(0)!!))?.div(openValue?.get(0)!!)?.times(100)
        //stockEvolv.text = "${evolv.toString()}%"

        val favorite = view.findViewById<ImageView>(R.id.favorite_button)
        val firestore = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser
        val favoriteRef = firestore.collection("users/${user?.uid}/favorites")
        favorite.setOnClickListener(){
            if (favorite.tag == "unselected"){
                favorite.setImageResource(R.drawable.favorite_selected)
                favorite.tag = "selected"
                val favoriteStock = hashMapOf(
                    "symbol" to displayInfo?.get(0),
                )
                favoriteRef.add(favoriteStock)
            }else{
                favorite.setImageResource(R.drawable.favorite_unselected)
                favorite.tag = "unselected"
                favoriteRef.whereEqualTo("symbol",displayInfo?.get(0)).get().addOnSuccessListener {
                    for (document in it){
                        favoriteRef.document(document.id).delete()
                    }
                }
            }
        }
        firestore.collection("users/${user?.uid}/favorites").whereEqualTo("symbol",displayInfo?.get(0)).get().addOnSuccessListener {
            if (it.isEmpty){
                favorite.setImageResource(R.drawable.favorite_unselected)
                favorite.tag = "unselected"
            }else{
                favorite.setImageResource(R.drawable.favorite_selected)
                favorite.tag = "selected"
            }
        }
        
    }

    companion object {

        @JvmStatic
        fun newInstance(
            displayInfo: ArrayList<String>,
            volumeValue: ArrayList<Int>?,
            origine : Char
        ) =
            StockDetailFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("displayInfo",displayInfo)
                    putIntegerArrayList("volumeValue",volumeValue)
                    putChar("origine", origine)
                }
            }
    }

    private fun buildChart(chartData : ArrayList<Double>, chart : com.github.aachartmodel.aainfographics.aachartcreator.AAChartView){
        val chartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Spline)
            .backgroundColor(Color.TRANSPARENT)
            .dataLabelsEnabled(false)
            .xAxisVisible(true)
            .yAxisVisible(true)
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
                    .data(chartData.toTypedArray())
            ))
        chart.aa_drawChartWithChartModel(chartModel)
    }
}