package com.example.littlebeachblog.data.api

import com.example.littlebeachblog.app.utils.TOKEN
import com.example.littlebeachblog.data.bean.AvatarBean
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.bean.ScoreBean
import com.example.littlebeachblog.data.config.ApiResponse
import retrofit2.http.*

/**
 * Created by AK on 2022/2/8 14:35.
 * God bless my code!
 */
interface UserAPI {

    /**
     * token检查
     */
    @POST("/ak")
    suspend fun checkToken(@Header("authorization") token: String): ApiResponse<String>

    @POST("/user/register")
    suspend fun register(@Body map: Map<String, String>): ApiResponse<String>

    /**
     * 用户登录
     */
    @POST("/user/login")
    suspend fun login(@Body loginData: LoginData): ApiResponse<String>

    /**
     * 修改密码
     */
    @PUT("/ak/user/password")
    suspend fun updatePwd(@Header(TOKEN) token: String, @Body map: Map<String, String>): ApiResponse<String>

    /**
     * 获取用户信息
     */
    @GET("/ak/user/userInfo")
    suspend fun userInfo(@Header("authorization") token: String): ApiResponse<MoYv.UserInfo>

    /**
     * 修改用户信息
     */
    @PUT("/ak/user/userInfo")
    suspend fun updateUserInfo(
        @Header(TOKEN) token: String,
        @Body userInfo: MoYv.UserInfo
    ): ApiResponse<MoYv.UserInfo>

    /**
     * 获取用户积分记录
     */
    @GET("/ak/score/list")
    suspend fun scoreList(@Header(TOKEN) token: String): ApiResponse<List<ScoreBean>>

    /**
     * 获取推荐头像
     */
    @GET("/avatar/recommend")
    suspend fun getAvatarRecommend(): ApiResponse<List<AvatarBean>>
}