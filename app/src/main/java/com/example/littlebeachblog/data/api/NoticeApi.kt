package com.example.littlebeachblog.data.api

import com.example.littlebeachblog.app.utils.TOKEN
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.data.config.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by AK on 2022/2/21 17:44.
 * God bless my code!
 */
interface NoticeApi {
    /**
     * 获取摸鱼点赞通知
     */
    @GET("/ak/moyv/like/msg")
    suspend fun getLikesById(@Header(TOKEN) id: String?): ApiResponse<LikeNoticeBean>

    /**
     * 单条消息已读
     */
    @PUT("/ak/moyv/readlike/{likeId}")
    suspend fun readOne(
        @Header(TOKEN) token: String,
        @Path("likeId") likeId: String
    ): ApiResponse<LikeNoticeBean.LikesBean>

    /**
     * 点赞一键已读
     */
    @PUT("/ak/moyv/readAll")
    suspend fun readAll(@Header(TOKEN) token: String): ApiResponse<String>
}