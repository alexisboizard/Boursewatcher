package com.alexisboiz.boursewatcher.views.auth

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.domain.AuthRepository
import com.alexisboiz.boursewatcher.domain.FavoriteRepository
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
    private var _favoriteLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val favoriteLiveData : LiveData<Boolean> = _favoriteLiveData

    fun putInFirestore(path : String, data : HashMap<String,String?>){
        viewModelScope.launch {
            FavoriteRepository.putInFirestore(path, data)
                .catch {
                    Log.e("FavoriteViewModel", "register: ${it.message}")
                }
                .collect(){
                    _favoriteLiveData.postValue(it)
                }
        }
    }
}
