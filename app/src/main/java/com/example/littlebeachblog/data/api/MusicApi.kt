package com.example.littlebeachblog.data.api

import com.example.littlebeachblog.data.bean.MusicUrlBean
import com.example.littlebeachblog.data.bean.TestAlbum
import com.example.littlebeachblog.data.config.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by AK on 2022/2/16 19:47.
 * God bless my code!
 */
interface MusicApi {

    /**
     * 获取歌单所有歌曲
     */
    @GET("/playlist/track/all")
    suspend fun getSongByTrackId(@Query("id") id: String = "7294860310"): TestAlbum

    /**
     * 获取音乐 url
     */
    @GET("/song/url")
    suspend fun getUriByMucId(
        @Query("id") id: String,
        @Query("br") br: String? = null
    ): ApiResponse<ArrayList<MusicUrlBean>>
}