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
import com.alexisboiz.boursewatcher.adapters.VerticalStockListAdapter
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel

class VerticalStockList : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    val stockViewModel by activityViewModels<StockViewModel>()
    var metaList : MutableList<Meta> = mutableListOf()
    var priceChartList : MutableList<ArrayList<Double>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_vertical_stock_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var recycler = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)


        recycler?.apply {
            this?.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this?.adapter = adapter
        }

        stockViewModel.stockLiveData.observe(viewLifecycleOwner){response ->
            metaList.add(response.stock?.chart?.result?.get(0)?.meta!!)
            priceChartList.add(response.chartData)
            recycler?.adapter = VerticalStockListAdapter(metaList,priceChartList)
            recycler?.adapter?.notifyDataSetChanged()
        }

    }

    companion object {
        @JvmStatic fun newInstance() =
                VerticalStockList()
    }

    override fun onRefresh() {

    }
}