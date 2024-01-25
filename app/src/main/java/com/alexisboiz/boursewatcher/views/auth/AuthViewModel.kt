package com.alexisboiz.boursewatcher.views.auth

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisboiz.boursewatcher.domain.AuthRepository
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private var _registerLiveData : MutableLiveData<UserDetail> = MutableLiveData()
    val registerLiveData : LiveData<UserDetail> = _registerLiveData

    fun register(email : String, password : String, avatar : ImageView, firstname :String, lastname : String){
        viewModelScope.launch {
            AuthRepository.register(email, password, avatar, firstname, lastname)
                .catch {
                    Log.e("AuthViewModel", "register: ${it.message}")
                }
                .collect(){
                    _registerLiveData.postValue(it)
                }
        }
    }
}
