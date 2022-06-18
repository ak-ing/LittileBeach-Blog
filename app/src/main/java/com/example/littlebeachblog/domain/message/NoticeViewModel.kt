package com.example.littlebeachblog.domain.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.littlebeachblog.app.utils.UILoader
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.repository.NoticeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by AK on 2022/2/21 19:21.
 * God bless my code!
 */
class NoticeViewModel : ViewModel() {

    val mMsgNotice = NoticeRepository.getInstance().getLatestMessages()
        .catch {
            emit(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
            it.printStackTrace()
        }.stateIn(
            viewModelScope, WhileSubscribed(5000),
            ApiResponse(UILoader.UIStatus.NONE)
        )

    val msgNoticeLd = MutableLiveData<ApiResponse<LikeNoticeBean>>()

    fun requestReadAllLike(token: String) =
        viewModelScope.launch(Dispatchers.IO) { NoticeRepository.getInstance().readAllLike(token) }
}