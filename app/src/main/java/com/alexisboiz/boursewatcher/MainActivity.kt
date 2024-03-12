package com.alexisboiz.boursewatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel

class MainActivity : AppCompatActivity() {
    val stockViewModel by viewModels<StockViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    companion object {
        var connected: Boolean = true
        val stockViewModel = StockViewModel()
    }
}
