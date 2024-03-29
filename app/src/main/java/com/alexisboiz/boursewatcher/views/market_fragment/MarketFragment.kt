package com.alexisboiz.boursewatcher.views.market_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.StocksInfoRepository
import com.alexisboiz.boursewatcher.model.StocksModel.StockInfo
import com.alexisboiz.boursewatcher.views.SearchActivity

class MarketFragment : Fragment() {
    private val stockViewModel by activityViewModels<StockViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view :View = inflater.inflate(R.layout.fragment_market, container, false)
        val horizontalRecycler = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        val stocksInfoRepository = context?.let { StocksInfoRepository(it) }


        horizontalRecycler?.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

        stockViewModel.dayGainersLiveData.observe(viewLifecycleOwner){response ->
            horizontalRecycler?.adapter = GainerListAdapter(response)
        }
        stockViewModel.getDayGainers()

        val verticalRecycler = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)

        verticalRecycler?.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }

        stockViewModel.stockLiveData.observe(viewLifecycleOwner){responseList ->
            verticalRecycler?.adapter = VerticalStockListAdapter(responseList)
        }

        val openSearchActivity = view.findViewById<Button>(R.id.search_view)
        openSearchActivity.setOnClickListener{
            val intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    companion object {
        fun newInstance() =
            MarketFragment()
    }
}