package com.alexisboiz.boursewatcher

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.alexisboiz.boursewatcher.model.TradedAsset.TradedAsset
import com.alexisboiz.boursewatcher.utils.LocaleManager
import com.alexisboiz.boursewatcher.views.market_fragment.StockViewModel
import com.alexisboiz.boursewatcher.views.AddStockActivity
import com.alexisboiz.boursewatcher.views.friends_fragment.FriendFragment
import com.alexisboiz.boursewatcher.views.MainFragment
import com.alexisboiz.boursewatcher.views.market_fragment.MarketFragment
import com.alexisboiz.boursewatcher.views.news_fragment.NewsFragment
import com.alexisboiz.boursewatcher.views.auth.RegisterFragment
import com.alexisboiz.boursewatcher.views.settings_fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    val stockViewModel by viewModels<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        val user = Firebase.auth.currentUser
        if(user == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, RegisterFragment.newInstance())
                .commitNow()
        }else{
            userId = user.uid
            val tradedAssetRef = database.getReference("tradedAsset_${user?.uid}")

            val navigation = findViewById<BottomNavigationView>(R.id.bottomAppBar)
            navigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, MainFragment.newInstance())
                            .commitNow()
                        true
                    }

                    R.id.action_market -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, MarketFragment.newInstance())
                            .commitNow()
                        true
                    }

                    R.id.action_friends -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, FriendFragment.newInstance())
                            .commitNow()
                        true
                    }

                    R.id.action_news -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, NewsFragment.newInstance())
                            .commitNow()
                        true
                    }

                    R.id.action_settings -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, SettingFragment.newInstance())
                            .commitNow()
                        true
                    }

                    else -> false
                }
            }
            AddStockActivity().tradedAssetLiveData.observe(this) {
                val id = tradedAssetRef.push().key
                val tradedAsset = id?.let { it1 ->
                    TradedAsset(
                        it1,
                        it.symbol,
                        it.purshasedPrice,
                        it.quantity,
                        it.purshasedDate
                    )
                }
                tradedAssetRef.setValue(tradedAsset)
            }


            val symbolList = mutableListOf("AAPL", "BTC-EUR", "TSLA", "AMZN", "GOOG", "MSFT")
            stockViewModel.fetchStock(symbolList)

            val firebaseMessaging = FirebaseMessaging.getInstance();
            val userTokenRef = Firebase.firestore.collection("users").document(user!!.uid)

            firebaseMessaging.token.addOnCompleteListener{ task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                }
                val token = task.result
                userTokenRef.update("token", token)
            }

            firebaseMessaging.subscribeToTopic("information-global")
        }
    }
    companion object {
        var connected: Boolean = false
        var userId = Any()
    }
}
