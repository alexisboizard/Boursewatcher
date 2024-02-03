package com.alexisboiz.boursewatcher.views.market_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.StockDetails
import com.alexisboiz.boursewatcher.domain.StockRepository
import com.alexisboiz.boursewatcher.model.Gainers
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StockViewModel: ViewModel() {
    private var _stockLiveData : MutableLiveData<MutableList<RecyclerHorizontalCard>> = MutableLiveData()
    val stockLiveData : LiveData<MutableList<RecyclerHorizontalCard>> = _stockLiveData

    private var _searchLiveData : MutableLiveData<ArrayList<Quotes>> = MutableLiveData()
    val searchLiveData : LiveData<ArrayList<Quotes>> = _searchLiveData

    private var _stockForWalletLiveData : MutableLiveData<MutableList<RecyclerHorizontalCard>> = MutableLiveData()
    val stockForWalletLiveData : LiveData<MutableList<RecyclerHorizontalCard>>  = _stockForWalletLiveData

    private var _dayGainersLiveData : MutableLiveData<ArrayList<Gainers>> = MutableLiveData()
    val dayGainersLiveData : LiveData<ArrayList<Gainers>> = _dayGainersLiveData

    private var _favoriteLiveData : MutableLiveData<MutableList<RecyclerHorizontalCard>> = MutableLiveData()
    val favoriteLiveData : LiveData<MutableList<RecyclerHorizontalCard>> = _favoriteLiveData

    private var _stockDetForSearch : MutableLiveData<MutableList<RecyclerHorizontalCard>> = MutableLiveData()
    val stockDetForSearch : LiveData<MutableList<RecyclerHorizontalCard>> = _stockDetForSearch

    private var _stockDetailForGainers : MutableLiveData<MutableList<RecyclerHorizontalCard>> = MutableLiveData()
    val stockDetailForGainers : LiveData<MutableList<RecyclerHorizontalCard>> = _stockDetailForGainers

    fun fetchStock(symbolList:MutableList<String>){
        val list = mutableListOf<RecyclerHorizontalCard>()
        for(symbol in symbolList){
            viewModelScope.launch {
                StockRepository.getStock(symbol)
                    .catch {
                        Log.e("StockViewModel", "fetchStock: ${it.message}")
                    }
                    .collect(){
                        val chartData : ArrayList<Double>? = it.body()?.data?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                        val stock = it.body()
                        chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1) }
                            ?.let { it2 -> list.add(it2) }
                        _stockLiveData.postValue(list)
                        }
                    }
            }
        }
    fun search(keyword : String){
        viewModelScope.launch {
            StockRepository.find(keyword)
                .catch {
                    Log.e("StockViewModel", "search: ${it.message}")
                }
                .collect{
                    _searchLiveData.postValue(it.body()?.quotes)
                }
        }
    }

    fun stockDetailForSearch(symbolList:MutableList<String>){
        val list = mutableListOf<RecyclerHorizontalCard>()
        for(symbol in symbolList){
            viewModelScope.launch {
                StockRepository.getStock(symbol)
                    .catch {
                        Log.e("StockViewModel", "stockDetailForSearch: ${it.message}")
                    }
                    .collect(){
                        val chartData : ArrayList<Double>? = it.body()?.data?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                        val stock = it.body()
                        chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1) }
                            ?.let { it2 -> list.add(it2) }
                        _stockDetForSearch.postValue(list)
                    }
            }
        }
    }

    fun getStockForWallet(symbolList:MutableList<String>){
        Log.d("StockViewModel", "getStockForWallet: $symbolList")
        val list = mutableListOf<RecyclerHorizontalCard>()
        for(symbol in symbolList){
            viewModelScope.launch {
                StockRepository.getStock(symbol)
                    .catch {
                        Log.e("StockViewModel", "getStockForWallet(): ${it.message}")
                    }
                    .collect(){
                        val chartData : ArrayList<Double>? = it.body()?.data?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                        val stock = it.body()
                        chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1) }
                            ?.let { it2 -> list.add(it2) }
                        _stockForWalletLiveData.postValue(list)
                    }
            }
        }
    }

    fun getDayGainers(){
        viewModelScope.launch {
            StockRepository.getDayGainers()
                .catch {
                    Log.e("StockViewModel", "getDayGainers(): ${it.message}")
                }
                .collect{
                    _dayGainersLiveData.postValue(it.body())
                }
        }
    }

    fun fetchFavoriteStock(symbolList:MutableList<String>){
        val list = mutableListOf<RecyclerHorizontalCard>()
        for(symbol in symbolList){
            viewModelScope.launch {
                StockRepository.getStock(symbol)
                    .catch {
                        Log.e("StockViewModel", "getFavoriteStock(): ${it.message}")
                    }
                    .collect{
                        val chartData : ArrayList<Double>? = it.body()?.data?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                        val stock : StockDetails? = it.body()
                        chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1) }
                            ?.let { it2 -> list.add(it2) }
                        _favoriteLiveData.postValue(list)
                    }
            }
        }
    }

    fun fetchGainerDetails(symbolList:MutableList<String>){
        val list = mutableListOf<RecyclerHorizontalCard>()
        for(symbol in symbolList){
            viewModelScope.launch {
                StockRepository.getStock(symbol)
                    .catch {
                        Log.e("StockViewModel", "fetchGainerDetails: ${it.message}")
                    }
                    .collect(){
                        val chartData : ArrayList<Double>? = it.body()?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                        val stock : Stock? = it.body()
                        chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1, "") }
                            ?.let { it2 -> list.add(it2) }
                        _stockDetailForGainers.postValue(list)
                    }
            }
        }
    }

}
