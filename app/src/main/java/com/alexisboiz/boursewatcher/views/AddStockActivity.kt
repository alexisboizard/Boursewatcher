package com.alexisboiz.boursewatcher.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.TradedAssetRepository
import com.alexisboiz.boursewatcher.model.TradedAsset.TradedAsset
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AddStockActivity : AppCompatActivity() {
    val tradedAssetLiveData : MutableLiveData<TradedAsset> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)
        val submitButton = findViewById<MaterialButton>(R.id.submit_stock)
        val symbol = findViewById<EditText>(R.id.et_company_code)
        val quantity = findViewById<EditText>(R.id.et_quantity)
        val price = findViewById<EditText>(R.id.et_purshase_price)
        val date = findViewById<EditText>(R.id.et_purshase_date)
        val firestore = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid

        submitButton.setOnClickListener(){
            // TODO : Validation des champs
            val hashmap = hashMapOf(
                "symbol" to symbol.text.toString(),
                "quantity" to quantity.text.toString(),
                "price" to price.text.toString(),
                "date" to date.text.toString(),
                "userId" to userId.toString()
            )
            firestore.collection("users/$userId/userwallet").add(hashmap)
                .addOnSuccessListener {
                    Log.d("AddStockActivity", "onCreate: ${it.id}")
                }
                .addOnFailureListener {
                    Log.e("AddStockActivity", "onCreate: ${it.message}")
                }
            finish()
        }
    }
}