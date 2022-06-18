package com.example.littlebeachblog.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.data.repository.NoticeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {

    val likesFl = MutableStateFlow<List<LikeNoticeBean.LikesBean>>(arrayListOf())

    fun requestReadOneLike(token: String, likeId: String) =
        viewModelScope.launch(Dispatchers.IO) { NoticeRepository.getInstance().readOneLike(token, likeId) }

}