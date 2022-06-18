package com.example.littlebeachblog.ui.state

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlebeachblog.data.bean.MoYv

class MeViewModel : ViewModel() {

    /**
     * 用户信息
     */
    val userInfo = MutableLiveData<MoYv.UserInfo>()

    /**
     * 退出登录按钮可见性
     */
    val logoutBtnVisibility = ObservableBoolean()

    val initTabAndPage = MutableLiveData(0)

    val refreshStateChanged = MutableLiveData<Boolean>()
}