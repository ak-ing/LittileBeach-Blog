package com.example.littlebeachblog.ui.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlebeachblog.data.bean.AvatarBean
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.repository.UserRepository
import com.example.littlebeachblog.data.repository.UtilRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val emailLd = MutableLiveData<String>()

    val pwdLd = MutableLiveData<String>()

    val pwdAgainLd = MutableLiveData<String>()

    val verifyCodeLd = MutableLiveData<String>()

    val loadState = MutableLiveData(false)

    val btnEnable = MutableLiveData<Boolean>()

    val verifyEnable = MutableLiveData<Boolean>(true)

    val verifyText = MutableLiveData("获取验证码")

    val verifyCode = MutableLiveData<String>()

    val loginLd = MutableLiveData<ApiResponse<String>>()

    val nickLd = MutableLiveData<String>()

    val avatarLd = MutableLiveData<String>()

    val avatarRecommend = MutableLiveData<ApiResponse<List<AvatarBean>>>()

    fun login(loginData: LoginData) {
        viewModelScope.launch { UserRepository.getInstance().login(loginData, loginLd::setValue) }
    }

    /**
     * 用户注册
     */
    suspend fun requestRegister(body: Map<String, String>) = UserRepository.getInstance().register(body)

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(token: String) = UserRepository.getInstance().getUserInfo(token)

    /**
     * 请求发送验证码到邮箱
     */
    suspend fun requestSendEmail(body: Map<String, String>) = UtilRepository.getInstance().sendEmail(body)

    /**
     * 请求修改密码
     */
    suspend fun requestUpdatePwd(body: Map<String, String>) = UserRepository.getInstance().updatePwd(body)

    /**
     * 获取推荐头像
     */
    fun requestAvatarRecommend() = viewModelScope.launch {
        UserRepository.getInstance().getAvatar(avatarRecommend::postValue)
    }


}