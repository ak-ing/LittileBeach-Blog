package com.example.littlebeachblog.ui.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlebeachblog.data.bean.ScoreBean
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.repository.UserRepository
import kotlinx.coroutines.launch

class ScoreViewModel : ViewModel() {

    private val scoreResponse = MutableLiveData<ApiResponse<List<ScoreBean>>>()

    val scoreListLd = MutableLiveData<List<ScoreBean>>()

    fun getScoreResponse() = scoreResponse

    fun requestScoreList() = viewModelScope.launch {
        UserRepository.getInstance().getScoreList(scoreResponse::setValue)
    }

}