package com.example.littlebeachblog.data.api

import com.example.littlebeachblog.app.utils.TOKEN
import com.example.littlebeachblog.data.bean.FishTagBean
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.config.ApiResponse
import okhttp3.MultipartBody
import okhttp3.internal.EMPTY_HEADERS
import retrofit2.http.*

/**
 * Created by AK on 2022/2/1 13:42.
 * God bless my code!
 */
interface HomeAPIs {

    //获取摸鱼列表
    @GET("/moyv/list")
    suspend fun getMoYvs(
        @Header(TOKEN) token: String? = null,
        @Query("num") num: Int?,
        @Query("size") size: Int?
    ): ApiResponse<List<MoYv>>

    //获取摸鱼话题列表
    @GET("/ct/moyu/topic")
    suspend fun getFishTags(): ApiResponse<List<FishTagBean>>

    //发布摸鱼
    @POST("/ak/moyv")
    suspend fun putFish(@Header(TOKEN) token: String, @Body fish: MoYv): ApiResponse<String>

    //摸鱼点赞
    @PUT("/ak/moyv/like/{fishId}")
    suspend fun doLikeFish(
        @Header(TOKEN) token: String?,
        @Path("fishId") fishId: String,
        @Body map: Map<String, String>
    ): ApiResponse<String>

}