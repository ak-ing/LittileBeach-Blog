package com.example.littlebeachblog.data.repository

import android.util.Log
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.UILoader
import com.example.littlebeachblog.data.api.NoticeApi
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.config.ServiceCreator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

/**
 * Created by AK on 2022/2/21 19:10.
 * God bless my code!
 */
class NoticeRepository private constructor() {
    companion object {
        private val mDataRepository = NoticeRepository()

        @JvmStatic
        fun getInstance() = mDataRepository
    }

    private val refreshIntervalMs: Long = 5000
    private val msgApi = ServiceCreator.create(NoticeApi::class.java)

    fun getLatestMessages(): Flow<ApiResponse<LikeNoticeBean>> = flow {
        while (true) {
            try {
                val likeMessage = msgApi.getLikesById(App.getApp().mMap["token"])
                emit(likeMessage)
                kotlinx.coroutines.delay(refreshIntervalMs)
            } catch (e: Exception) {
                emit(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
                e.printStackTrace()
            }
        }
    }

    /**
     * 单条点赞已读
     */
    suspend fun readOneLike(token: String, likeId: String) {
        try {
            val readOne = msgApi.readOne(token, likeId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 点赞一键已读
     */
    suspend fun readAllLike(token: String) {
        try {
            val readAll = msgApi.readAll(token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}