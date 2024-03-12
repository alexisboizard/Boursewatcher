package com.alexisboiz.boursewatcher.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.VerticalStockListAdapter
import com.alexisboiz.boursewatcher.domain.TradedAssetRepository
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.viewmodel.StockViewModel
import com.google.android.material.button.MaterialButton
import kotlin.math.round

class WalletFragment : Fragment() {
    val stockViewModel by activityViewModels<StockViewModel>()
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
        return inflater.inflate(R.layout.fragment_wallet, container, false)
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

        // Gestion de la recycler view
        // Si symbole contenu dans le livedata on affiche
        // Sinon TODO : Rechercher dans l'API

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.wallet_recycler)

        recycler.apply {
            this?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            this?.adapter = adapter
        }
        var quantity : Double = 0.0
        var walletAmount : Double = 0.0
        var purshasePrice : Double = 0.0
        var purshaseWalletAmount : Double = 0.0
        var calcul = 0.0
        val tradedAssetList = tradedAssetRepository.getAllTradedAsset()

        for (tradedAsset in tradedAssetList) {
            purshaseWalletAmount += tradedAsset.purshasedPrice * tradedAsset.quantity
        }

        stockViewModel.stockForWalletLiveData.observe(viewLifecycleOwner){response ->
            metaList.add(response.stock?.chart?.result?.get(0)?.meta!!)
            priceChartList.add(response.chartData)
            recycler?.adapter = VerticalStockListAdapter(metaList,priceChartList)
            recycler?.adapter?.notifyDataSetChanged()

            // Calcul du montant du portefeuille
            walletAmount += response.stock.chart.result[0].meta?.regularMarketPrice?.times(quantity)!!
            walletAmountTV.text = walletAmount.toString() + "€"

            // Calcul de l'évolution du portefeuille

            calcul = walletAmount - purshaseWalletAmount
            calcul = calcul / purshaseWalletAmount
            calcul = calcul * 100
            walletEvolvTV.text = round(calcul).toString() + "%"

        }

        for (tradedAsset in tradedAssetList) {
            stockViewModel.getStockForWallet(tradedAsset.symbol)
            quantity = tradedAsset.quantity
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WalletFragment()
    }
}