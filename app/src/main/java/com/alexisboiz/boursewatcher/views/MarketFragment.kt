package com.alexisboiz.boursewatcher.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel

class MarketFragment : Fragment() {
    val stockViewModel by activityViewModels<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_market, container, false)
        val openSearchActivity = view.findViewById<Button>(R.id.search_view)
        openSearchActivity.setOnClickListener(){
            val intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
        stockViewModel.fetchStock("AAPL")
        stockViewModel.fetchStock("BTC-EUR")
        stockViewModel.fetchStock("TSLA")
        stockViewModel.fetchStock("AMZN")
        stockViewModel.fetchStock("GOOG")
        stockViewModel.fetchStock("MSFT")
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