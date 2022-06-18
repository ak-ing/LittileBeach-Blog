package com.example.littlebeachblog.domain.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.domain.request.MusicRequest

/**
 * Created by AK on 2022/2/9 20:50.
 * God bless my code!
 */
class SharedViewModel : ViewModel() {

    /**
     * token
     */
    val token = MutableLiveData<String>()

    /**
     * 用户信息
     */
    val userInfo = MutableLiveData<MoYv.UserInfo>()

    /**
     * 摸鱼列表是否刷新
     */
    val moYvRefresh = MutableLiveData(false)

    /**
     *  摸鱼条目
     */
    val fishItem = MutableLiveData<MoYv>()

    /**
     * 音乐请求
     */
    val musicRequest: MusicRequest = MusicRequest()

    val toAddSlideListener = MutableLiveData<Boolean>()

    val toCloseSlidePanelIfExpanded = MutableLiveData<Boolean>()

    val toCloseActivityIfAllowed = MutableLiveData<Boolean>()

}