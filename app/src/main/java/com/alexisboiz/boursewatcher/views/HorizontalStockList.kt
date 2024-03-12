package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.HorizontalStockListAdapter
import com.alexisboiz.boursewatcher.model.DayGainersModel.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.viewmodel.LogoViewModel
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.converter.gson.GsonConverterFactory

class HorizontalStockList : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    val stockViewModel by activityViewModels<StockViewModel>()
    val logoViewModel by activityViewModels<LogoViewModel>()
    var metaList : MutableList<Meta> = mutableListOf()
    var priceChartList : MutableList<ArrayList<Double>> = mutableListOf()
    var quotesList : MutableList<Quotes> = mutableListOf()
    val imageUriList : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val horizontalRecycler = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)

        horizontalRecycler?.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

        stockViewModel.dayGainersLiveData.observe(viewLifecycleOwner){response ->
            //response.finance.result[0].quotes
            Log.e("HSL", response.finance?.result?.get(0)?.quotes.toString())
            quotesList.addAll(response.finance?.result?.get(0)?.quotes!!)
            horizontalRecycler?.adapter = HorizontalStockListAdapter(quotesList, imageUriList)
        }
        stockViewModel.getDayGainers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horizontal_stock_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HorizontalStockList()
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}