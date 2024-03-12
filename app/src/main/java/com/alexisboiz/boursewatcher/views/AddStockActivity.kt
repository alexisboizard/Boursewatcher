package com.alexisboiz.boursewatcher.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.TradedAssetRepository
import com.alexisboiz.boursewatcher.model.TradedAsset.TradedAsset
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AddStockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)
        val submitButton = findViewById<MaterialButton>(R.id.submit_stock)
        val symbol = findViewById<EditText>(R.id.et_company_code)
        val quantity = findViewById<EditText>(R.id.et_quantity)
        val price = findViewById<EditText>(R.id.et_purshase_price)
        val date = findViewById<EditText>(R.id.et_purshase_date)
        val tradedAssetRepository = TradedAssetRepository(this)


        submitButton.setOnClickListener(){
            // TODO : Validation des champs
            val tradeAsset = TradedAsset(
                symbol.text.toString(),
                price.text.toString().toDouble(),
                quantity.text.toString().toDouble(),
                date.text.toString()
            )
            MainScope().launch {
                tradedAssetRepository.insertTradedAsset(tradeAsset)
                finish()
            }
        }
    }
}