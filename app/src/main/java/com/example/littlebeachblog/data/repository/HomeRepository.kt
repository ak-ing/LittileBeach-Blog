package com.example.littlebeachblog.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.PAGE_SIZE
import com.example.littlebeachblog.app.utils.UILoader
import com.example.littlebeachblog.data.api.HomeAPIs
import com.example.littlebeachblog.data.bean.FishTagBean
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.config.ApiResponse.Result
import com.example.littlebeachblog.data.config.ServiceCreator
import com.example.littlebeachblog.domain.paging.MoYvPagingSource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

/**
 * Created by AK on 2022/2/2 20:25.
 * God bless my code!
 */
class HomeRepository private constructor() {

    companion object {
        private val mDataRepository = HomeRepository()

        @JvmStatic
        fun getInstance() = mDataRepository
    }

    private val homeApi = ServiceCreator.create(HomeAPIs::class.java)

    private val sobApi = ServiceCreator.createSOB(HomeAPIs::class.java)

    /**
     * 获取摸鱼列表
     */
    fun getMoYvs(): Flow<PagingData<MoYv>> =
        Pager(
            config = PagingConfig(PAGE_SIZE, initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = { MoYvPagingSource(homeApi) }).flow

    suspend fun getFishTags(result: Result<List<FishTagBean>>) {
        try {
            val response = sobApi.getFishTags()
            result.onResult(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 发布摸鱼
     */
    suspend fun putFish(token: String, fish: MoYv, result: Result<String>) {
        try {
            val putFish = homeApi.putFish(token, fish)
            result.onResult(putFish)
        } catch (e: Exception) {
            e.printStackTrace()
            result.onResult(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
        }
    }

    /**
     * 摸鱼点赞
     */
    suspend fun doLikeFish(fishId: String, body: Map<String, String>) {
        try {
            val response = homeApi.doLikeFish(App.getApp().mMap["token"], fishId, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}