package com.alexisboiz.boursewatcher.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.domain.StockRepository
import com.alexisboiz.boursewatcher.model.DayGainersModel.Gainer
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.StocksModel.Meta
import com.alexisboiz.boursewatcher.model.StocksModel.RecyclerHorizontalCard
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StockViewModel: ViewModel() {
    private var _stockLiveData : MutableLiveData<RecyclerHorizontalCard> = MutableLiveData()
    val stockLiveData : LiveData<RecyclerHorizontalCard> = _stockLiveData

    private var _searchLiveData : MutableLiveData<ArrayList<Quotes>> = MutableLiveData()
    val searchLiveData : LiveData<ArrayList<Quotes>> = _searchLiveData

    private var _stockForWalletLiveData : MutableLiveData<RecyclerHorizontalCard> = MutableLiveData()
    val stockForWalletLiveData : LiveData<RecyclerHorizontalCard>  = _stockForWalletLiveData

    private var _dayGainersLiveData : MutableLiveData<Gainer> = MutableLiveData()
    val dayGainersLiveData : LiveData<Gainer> = _dayGainersLiveData
    fun fetchStock(symbole:String){
        viewModelScope.launch {
            StockRepository.getStock(symbole)
                .catch {
                    Log.e("StockViewModel", "fetchStock: ${it.message}")
                }
                .collect(){
                    val chartData : ArrayList<Double>? = it.body()?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                    val stock : Stock? = it.body()
                    _stockLiveData.postValue(chartData?.let { it1 ->
                        RecyclerHorizontalCard(
                            stock,
                            it1
                        )
                    })
                }
        }
    }

    fun search(keyword : String){
        viewModelScope.launch {
            StockRepository.find(keyword)
                .catch {
                    Log.e("StockViewModel", "search: ${it.message}")
                }
                .collect(){
                    _searchLiveData.postValue(it.body()?.quotes)
                }
        }
    }

    fun getStock(symbole: String) : Meta {
        var meta : Meta = Meta()
        viewModelScope.launch {
            StockRepository.getStock(symbole)
                .catch {
                    Log.e("StockViewModel", "getStock(): ${it.message}")
                }
                .collect(){
                    meta = it.body()?.chart?.result?.get(0)?.meta!!
                }
        }
        return meta
    }

    fun getStockForWallet(symbole: String){
        viewModelScope.launch {
            StockRepository.getStock(symbole)
                .catch {
                    Log.e("StockViewModel", "getStockForWallet(): ${it.message}")
                }
                .collect(){
                    val chartData : ArrayList<Double>? = it.body()?.chart?.result?.get(0)?.indicators?.quote?.get(0)?.open
                    val stock : Stock? = it.body()
                    _stockForWalletLiveData.postValue(chartData?.let { it1 -> RecyclerHorizontalCard(stock, it1) })
                }
        }
    }

    fun getDayGainers(){
        viewModelScope.launch {
            StockRepository.getDayGainers()
                .catch {
                    Log.e("StockViewModel", "getDayGainers(): ${it.message}")
                }
                .collect(){

                    _dayGainersLiveData.postValue(it.body())
                }
        }
    }


}
