package com.alexisboiz.boursewatcher.views.market_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.GainerListAdapter
import com.alexisboiz.boursewatcher.adapters.VerticalStockListAdapter
import com.alexisboiz.boursewatcher.model.DayGainersModel.Quotes
import com.alexisboiz.boursewatcher.viewmodel.LogoViewModel
import com.alexisboiz.boursewatcher.views.SearchActivity

class MarketFragment : Fragment() {
    val stockViewModel by activityViewModels<StockViewModel>()
    val logoViewModel by activityViewModels<LogoViewModel>()
    var quotesList : MutableList<Quotes> = mutableListOf()
    var quotesListVertical : MutableList<Quotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_market, container, false)
        val horizontalRecycler = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)

        horizontalRecycler?.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
        val symbolListHorizontal = mutableListOf<String>()
        stockViewModel.dayGainersLiveData.observe(viewLifecycleOwner){response ->
            //response.finance.result[0].quotes
            Log.e("HSL", response.finance?.result?.get(0)?.quotes.toString())
            quotesList = response.finance?.result?.get(0)?.quotes!!
            logoViewModel.logoHorizontalLiveData.observe(viewLifecycleOwner){
                horizontalRecycler?.adapter = GainerListAdapter(quotesList, it)
            }
            for(quote in quotesList){
                quote.symbol?.let { symbolListHorizontal.add(it) }
            }
            logoViewModel.getLogoUri(symbolListHorizontal)
        }
        stockViewModel.getDayGainers()

        var verticalRecycler = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)

        verticalRecycler?.apply {
            this?.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this?.adapter = adapter
        }

        val symbolListVerical = mutableListOf<String>()

        stockViewModel.stockLiveData.observe(viewLifecycleOwner){responseList ->
            logoViewModel.logoVerticalLiveData.observe(viewLifecycleOwner){
                verticalRecycler?.adapter = VerticalStockListAdapter(responseList,it)
            }
            for(response in responseList){
                response?.stock?.chart?.result?.get(0)?.meta?.symbol?.let { symbolListVerical.add(it) }
            }
            logoViewModel.getLogoUriVertical(symbolListVerical)
        }


        val openSearchActivity = view.findViewById<Button>(R.id.search_view)
        openSearchActivity.setOnClickListener(){
            val intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() =
            MarketFragment()
    }
}