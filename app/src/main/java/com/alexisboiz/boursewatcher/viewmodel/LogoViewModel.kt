package com.alexisboiz.boursewatcher.viewmodel

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
    private var _logoLiveData : MutableLiveData<LogoList> = MutableLiveData()
    val logoLiveData : LiveData<LogoList> = _logoLiveData

    fun getLogoUri(symbole: String) : String{
        var uri = ""
        viewModelScope.launch {
            LogoRepository.getLogo(symbole)
                .catch {
                    Log.e("NewsViewModel", "fetchNews: ${it}")
                }
                .collect(){
                    uri = it.body()?.logos?.get(0)?.files?.original.toString()
                }
        }
        return uri
    }
}
