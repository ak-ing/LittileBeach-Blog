package com.example.littlebeachblog.data.api

import com.example.littlebeachblog.app.utils.TOKEN
import com.example.littlebeachblog.data.bean.ImageBean
import com.example.littlebeachblog.data.config.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by AK on 2022/2/11 17:11.
 * God bless my code!
 */
interface UtilApi {

    //上传图片
    @Multipart
    @POST("/ak/image/upload")
    suspend fun uploadImgs(
        @Header(TOKEN) token: String,
        @Part image: List<MultipartBody.Part>
    ): ApiResponse<List<ImageBean>>

    /**
     * 发送邮箱验证码
     */
    @POST("/email/captcha")
    suspend fun sendEmail(@Body map: Map<String, String>): ApiResponse<String>
}