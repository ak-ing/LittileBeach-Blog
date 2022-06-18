package com.example.littlebeachblog.ui.state

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlebeachblog.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val initTabAndPage = MutableLiveData(0)

    val avatarUrl = MutableLiveData("")

    suspend fun checkToken(token: String) = UserRepository.getInstance().checkToken(token)
}