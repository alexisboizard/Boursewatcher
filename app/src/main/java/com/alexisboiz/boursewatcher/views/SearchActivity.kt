package com.alexisboiz.boursewatcher.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.SearchAdapter
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel

class SearchActivity : AppCompatActivity() {
    val stockViewModel by viewModels<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchView = findViewById<SearchView>(R.id.search_view)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.search_recycler_view)
        var listStock : MutableList<Meta> = mutableListOf()

        recyclerView.apply {
            this.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
            this.adapter = adapter
        }

        searchView.requestFocusFromTouch()
        stockViewModel.searchLiveData.observe(this){
            Log.e("SearchActivity", "onCreate: $it")
            recyclerView.adapter = SearchAdapter(it, listStock)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                stockViewModel.search(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                stockViewModel.search(newText!!)
                return false
            }
        })
    }
}