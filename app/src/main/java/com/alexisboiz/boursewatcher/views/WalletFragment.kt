package com.alexisboiz.boursewatcher.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.views.market_fragment.VerticalStockListAdapter
import com.alexisboiz.boursewatcher.domain.TradedAssetRepository
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.alexisboiz.boursewatcher.model.TradedAsset.TradedAsset
import com.alexisboiz.boursewatcher.viewmodel.LogoViewModel
import com.alexisboiz.boursewatcher.views.market_fragment.StockViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class WalletFragment : Fragment() {
    val stockViewModel by activityViewModels<StockViewModel>()
    val logoViewModel by activityViewModels<LogoViewModel>()
    var metaList : MutableList<Meta> = mutableListOf()
    var priceChartList : MutableList<ArrayList<Double>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addStockButton = view.findViewById<MaterialButton>(R.id.add_stock_button)
        val tradedAssetRepository = TradedAssetRepository(this.requireContext())


        addStockButton.setOnClickListener() {
            val intent = Intent(requireActivity(), AddStockActivity::class.java)
            startActivity(intent)
        }
        val walletAmountTV = view.findViewById<TextView>(R.id.wallet_resume_amount)
        val walletEvolvTV = view.findViewById<TextView>(R.id.wallet_evolv_amount)

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.wallet_recycler)

        recycler.apply {
            this?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            this?.adapter = adapter
        }

        val tradedAssetList = mutableListOf<TradedAsset>()
        val symboleList = mutableListOf<String>()
        val stockList = mutableListOf<RecyclerHorizontalCard>()

        val firestore = Firebase.firestore
        val userID = Firebase.auth.currentUser?.uid!!

        var purshaseWalletAmount : Double = 0.0
        var walletAmount : Double? = 0.0


        firestore.collection("users/$userID/userwallet").get()
            .addOnSuccessListener {
                    recycler.adapter?.let { recycler.adapter!!.notifyItemRangeRemoved(0, it.itemCount)}
                    stockList.clear()
                    for (document in it){
                        if (document.data["userId"] == userID){
                            tradedAssetList.add(
                                    TradedAsset(
                                        document.id,
                                        document.data["symbol"].toString(),
                                        document.data["quantity"].toString().toDouble(),
                                        document.data["price"].toString().toDouble(),
                                        document.data["date"].toString(),
                                    )
                            )
                            symboleList.add(document.data["symbol"].toString())
                            purshaseWalletAmount += document.data["price"].toString().toDouble() * document.data["quantity"].toString().toDouble()
                            if (walletAmount != null) {
                                stockViewModel.stockForWalletLiveData.observe(viewLifecycleOwner){responseList ->
                                    for(response in responseList){
                                        stockList.add(response)
                                        walletAmount += response.stock?.chart?.result?.get(0)?.meta?.regularMarketPrice!! * document.data["quantity"].toString().toDouble()
                                    }
                                    walletAmountTV.text = walletAmount.toString() + "€"
                                    Log.e("WalletFragment", "onViewCreated: $stockList")
                                    recycler?.adapter = VerticalStockListAdapter(
                                        stockList,
                                        mutableListOf()
                                    )
                                }
                            }
                        }
                }
                stockViewModel.getStockForWallet(symboleList)
            }
            .addOnFailureListener {
                // TODO : Gérer l'erreur
            }
    }

    override fun onStop() {
        super.onStop()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            WalletFragment()
    }
}