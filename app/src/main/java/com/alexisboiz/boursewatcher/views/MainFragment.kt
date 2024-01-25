package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.FavoriteListAdapter
import com.alexisboiz.boursewatcher.views.market_fragment.StockViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val walletDetail = view.findViewById<MaterialButton>(R.id.wallet_details)
        val horizontalRV = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view_main)
        val userId = Firebase.auth.currentUser?.uid
        val favRef = Firebase.firestore.collection("users/${userId}/userwallet")
        val userWalletList = mutableListOf<String>()
        val stockQuantityList = mutableListOf<Double>()
        val walletValueTV = view.findViewById<TextView>(R.id.tv_account_balance_value)
        var walletValue : Double? = 0.0
        val stockViewModel by activityViewModels<StockViewModel>()

        walletDetail.setOnClickListener(){
            val fragment = WalletFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit()
        }

        horizontalRV.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
        favRef.get()
            .addOnSuccessListener {
                userWalletList.clear()
                stockQuantityList.clear()
                for (document in it){
                    userWalletList.add(document.data["symbol"].toString())
                    stockQuantityList.add(document.data["quantity"].toString().toDouble())
                }
                // Gestion des infos du portefeuille
                stockViewModel.stockForWalletLiveData.observe(viewLifecycleOwner){
                    walletValue = 0.0
                    for(stock in it){
                        if (walletValue != null) {
                            stockQuantityList.forEachIndexed { index, qte ->
                                Log.d("qte", qte.toString())
                                Log.d("walletValue", walletValue.toString())
                                walletValue = walletValue!! + stock.stock!!.chart!!.result.get(0).meta!!.regularMarketPrice!!.times(qte)
                            }
                        }
                    }
                    walletValue = ((walletValue!! * 100.0).toInt() / 100.0)
                    walletValueTV.text = walletValue.toString() + "€"
                }
                stockViewModel.getStockForWallet(userWalletList)
                // Gestion des favoris
                stockViewModel.favoriteLiveData.observe(viewLifecycleOwner){
                    horizontalRV.adapter = FavoriteListAdapter(it)
                }
                stockViewModel.fetchFavoriteStock(userWalletList)
            }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}