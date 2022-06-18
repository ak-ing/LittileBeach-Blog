package com.example.littlebeachblog.ui.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.repository.UserRepository
import com.example.littlebeachblog.data.repository.UtilRepository
import okhttp3.MultipartBody

/**
 * Created by AK on 2022/3/2 19:53.
 * God bless my code!
 */
class PersonViewModel : ViewModel() {

    val userInfoLd = MutableLiveData<MoYv.UserInfo>()

    val sexLd = MutableLiveData<String>()

    val labelLd = MutableLiveData<String>()

    suspend fun updateUserInfo(userInfo: MoYv.UserInfo) =
        UserRepository.getInstance().updateUserInfo(userInfo)

    suspend fun upLoadAvatar(image: List<MultipartBody.Part>) =
        UtilRepository.getInstance().uploadAvatar(image)

}