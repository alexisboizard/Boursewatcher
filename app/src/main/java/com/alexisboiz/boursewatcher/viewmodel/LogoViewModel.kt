package com.alexisboiz.boursewatcher.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.domain.LogoRepository
import com.alexisboiz.boursewatcher.domain.NewsRepository
import com.alexisboiz.boursewatcher.model.LogoModel.LogoList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LogoViewModel: ViewModel() {
    private var _logoVericalLiveData : MutableLiveData<MutableList<String>> = MutableLiveData()
    val logoVerticalLiveData : LiveData<MutableList<String>> = _logoVericalLiveData

    private var _logoHorizontalLiveData : MutableLiveData<MutableList<String>> = MutableLiveData()
    val logoHorizontalLiveData : LiveData<MutableList<String>> = _logoHorizontalLiveData

    private var _logoForWallet : MutableLiveData<MutableList<Map<String,String>>> = MutableLiveData()
    val logoForWallet: LiveData<MutableList<Map<String,String>>> = _logoForWallet

    fun getLogoUri(listSymbol: MutableList<String>) : MutableList<String>{
        val uri = mutableListOf<String>()
        for(symbol in listSymbol){
            viewModelScope.launch {
                LogoRepository.getLogo(symbol)
                    .catch {
                        Log.e("LogoViewModel", "fetchNews: ${it}")
                    }
                    .collect(){
                        uri.add(it.body()?.logos?.get(0)?.files?.original.toString())
                        _logoHorizontalLiveData.postValue(uri)
                    }
            }
        }
        return uri
    }
    fun getLogoUriVertical(listSymbol: MutableList<String>) : MutableList<String>{
        val uri = mutableListOf<String>()
        for(symbol in listSymbol){
            viewModelScope.launch {
                LogoRepository.getLogoVertical(symbol)
                    .catch {
                        Log.e("LogoViewModel", "fetchNews: ${it}")
                    }
                    .collect(){
                        uri.add(it.body()?.logos?.get(0)?.files?.original.toString())
                        _logoVericalLiveData.postValue(uri)
                    }
            }
        }
        return uri
    }

    fun fecthLogoForWallet(listSymbol: MutableList<String>) {
        val list = mutableListOf<Map<String,String>>()
        for(symbol in listSymbol){
            viewModelScope.launch {
                LogoRepository.getLogoVertical(symbol)
                    .catch {
                        Log.e("LogoViewModel", "fetchNews: ${it}")
                    }
                    .collect(){
                        list.add(
                            mapOf(
                                symbol to it.body()?.logos?.get(0)?.files?.original.toString()
                            )
                        )
                        _logoForWallet.postValue(list)
                    }
            }
        }
    }
}
