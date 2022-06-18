package com.example.littlebeachblog.data.repository

import android.util.Log
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.UILoader
import com.example.littlebeachblog.data.api.UtilApi
import com.example.littlebeachblog.data.bean.ImageBean
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.config.ServiceCreator
import okhttp3.MultipartBody
import java.lang.Exception

/**
 * Created by AK on 2022/2/11 17:11.
 * God bless my code!
 */
class UtilRepository private constructor() {
    companion object {
        private val mDataRepository = UtilRepository()

        @JvmStatic
        fun getInstance() = mDataRepository
    }

    private val utilApi = ServiceCreator.create(UtilApi::class.java)

    /**
     * 上传图片
     */
    suspend fun uploadImg(
        token: String,
        image: List<MultipartBody.Part>,
        result: ApiResponse.Result<List<ImageBean>>
    ) {
        try {
            val response = utilApi.uploadImgs(token, image)
            Log.d("TAG", "uploadImg: $response")
            result.onResult(response)
        } catch (e: Exception) {
            e.printStackTrace()
            result.onResult(ApiResponse(UILoader.UIStatus.NETWORK_ERROR))
        }
    }

    /**
     * 上传头像
     */
    suspend fun uploadAvatar(image: List<MultipartBody.Part>): ApiResponse<List<ImageBean>> {
        return try {
            val response = utilApi.uploadImgs(App.getApp().mMap["token"]!!, image)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

    /**
     * 发送邮箱验证码
     */
    suspend fun sendEmail(body: Map<String, String>): ApiResponse<String> {
        return try {
            utilApi.sendEmail(body)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(UILoader.UIStatus.NETWORK_ERROR)
        }
    }

}