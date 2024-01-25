package com.alexisboiz.boursewatcher.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.SearchAdapter
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.views.market_fragment.StockViewModel

class SearchActivity : AppCompatActivity() {
    val stockViewModel by viewModels<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchView = findViewById<SearchView>(R.id.search_view)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)
        val symbolList : MutableList<String> = mutableListOf()
        var itemList : MutableList<Quotes> = mutableListOf()

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter
        }

        searchView.requestFocusFromTouch()

        stockViewModel.stockDetForSearch.observe(this){
            Log.e("SearchActivity", "onCreate: $it")
            recyclerView.adapter = SearchAdapter(itemList,it)
        }

        stockViewModel.searchLiveData.observe(this){
            Log.e("SearchActivity", "onCreate: $it")
            if(it != null){
                itemList = it
                for(stock in it){
                    symbolList.add(stock.symbol!!)
                }
                stockViewModel.stockDetailForSearch(symbolList)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if(query.isNotEmpty()){
                        stockViewModel.search(query!!)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if(newText.isNotEmpty()){
                        stockViewModel.search(newText!!)
                    }
                }
                return false
            }
        })
    }
}