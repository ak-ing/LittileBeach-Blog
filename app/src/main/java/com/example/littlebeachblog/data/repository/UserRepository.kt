package com.example.littlebeachblog.data.repository

import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.UILoader
import com.example.littlebeachblog.data.api.UserAPI
import com.example.littlebeachblog.data.bean.AvatarBean
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.bean.ScoreBean
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.config.ServiceCreator
import java.lang.Exception

/**
 * Created by AK on 2022/2/8 14:36.
 * God bless my code!
 */
class UserRepository {
    private val userApi = ServiceCreator.create(UserAPI::class.java)

    companion object {
        private val mDataRepository = UserRepository()

        @JvmStatic
        fun getInstance() = mDataRepository
    }

    suspend fun checkToken(token: String) = try {
        userApi.checkToken(token)
    } catch (e: Exception) {
        ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
    }

    /**
     * 用户注册
     */
    suspend fun register(body: Map<String, String>): ApiResponse<String> {
        return try {
            val register = userApi.register(body)
            register
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

    /**
     * 用户登录
     */
    suspend fun login(login: LoginData, result: ApiResponse.Result<String>) {
        try {
            val response = userApi.login(login)
            result.onResult(response)
        } catch (e: Exception) {
            e.printStackTrace()
            result.onResult(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
        }
    }

    //获取用户信息
    suspend fun getUserInfo(token: String): ApiResponse<MoYv.UserInfo> {
        return try {
            userApi.userInfo(token)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

    //修改用户信息
    suspend fun updateUserInfo(userInfo: MoYv.UserInfo): ApiResponse<MoYv.UserInfo> {
        return try {
            val updateUserInfo = userApi.updateUserInfo(App.getApp().mMap["token"]!!, userInfo)
            updateUserInfo
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

    //获取用户积分记录
    suspend fun getScoreList(result: ApiResponse.Result<List<ScoreBean>>) {
        try {
            val response = userApi.scoreList(App.getApp().mMap["token"]!!)
            result.onResult(response)
        } catch (e: Exception) {
            e.printStackTrace()
            result.onResult(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
        }
    }

    /**
     * 修改密码
     */
    suspend fun updatePwd(body: Map<String, String>): ApiResponse<String> {
        return try {
            userApi.updatePwd(App.getApp().mMap["token"]!!, body)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

    /**
     * 获取推荐头像
     */
    suspend fun getAvatar(result: ApiResponse.Result<List<AvatarBean>>) {
        try {
            val avatarRecommend = userApi.getAvatarRecommend()
            result.onResult(avatarRecommend)
        } catch (e: Exception) {
            e.printStackTrace()
            result.onResult(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
        }
    }
}