package com.alexisboiz.boursewatcher.views.news_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.domain.NewsRepository
import com.alexisboiz.boursewatcher.model.NewsModel.News
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private var _newsLiveData : MutableLiveData<List<News>> = MutableLiveData()
    val newsLiveData : LiveData<List<News>> = _newsLiveData

    fun fetchNews(minId:Int, category: String){
        viewModelScope.launch {
            NewsRepository.getNews(minId, category)
                .catch {
                    Log.e("NewsViewModel", "fetchNews: ${it}")
                }
                .collect(){
                    _newsLiveData.postValue(it.body())
                }
        }
    }
}
