package com.alexisboiz.boursewatcher.views.news_fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.NewsAdapter

class NewsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var category : String = "general"
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view)
        val spinner = view.findViewById<androidx.appcompat.widget.AppCompatSpinner>(R.id.spinner)
        var spinner_adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(spinner)
        {
            adapter = spinner_adapter
            setSelection(0, false)
            onItemSelectedListener = this.onItemSelectedListener
            prompt = "Sélectionner une catégorie"
            gravity = Gravity.CENTER

        }
        recycler.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            adapter = adapter
        }

        newsViewModel.newsLiveData.observe(viewLifecycleOwner){
            recycler.adapter = NewsAdapter(it)
            recycler.adapter?.notifyDataSetChanged()
        }
        newsViewModel.fetchNews(0, "general")

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        category = p0?.getItemAtPosition(p2).toString()
        when(category){
            "Géneral" -> category = "general"
            "Forex" -> category = "forex"
            "Crypto" -> category = "crypto"
            "Fusion" -> category = "merger"
        }
        newsViewModel.fetchNews(0, category)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        category = "general"
        newsViewModel.fetchNews(0, category)
    }
}