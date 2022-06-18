package com.example.littlebeachblog.data.repository

import android.util.Log
import com.example.littlebeachblog.data.api.MusicApi
import com.example.littlebeachblog.data.bean.DownloadFile
import com.example.littlebeachblog.data.bean.LibraryInfo
import com.example.littlebeachblog.data.bean.TestAlbum
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.config.ServiceCreator.createMus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.util.*

/**
 * Created by AK on 2022/2/16 20:10.
 * God bless my code!
 */
class MusicRepository {
    companion object {
        private val mMusicRepository = MusicRepository()

        @JvmStatic
        fun getInstance() = mMusicRepository
    }

    private val musicApi = createMus(MusicApi::class.java)

    suspend fun getFreeMusic(result: TestAlbum.Result) {
        try {
            val response = musicApi.getSongByTrackId()
            val str = response.songs.map { it.id }
            val urlById = getUrlById(str)
            response.songs.mapIndexed { i, it -> it.url = urlById[i] }
            result.onResult(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获得歌曲url
     */
    private suspend fun getUrlById(id: List<String>): List<String> {
        return try {
            val str = id.toString()
            val s = str.slice(1 until str.length - 1)
            val response = musicApi.getUriByMucId(s)
            val a = response.getData()
            Log.d("TAG", "getUrlById: ${a.toString()}")
            response.getData().sortedBy { id.indexOf(it.id) }.map { it.url }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "getUrlById: ${e.message}")
            listOf()
        }
    }


    fun getLibraryInfo(result: ApiResponse.Result<List<LibraryInfo?>?>?) {
        val gson = Gson()
        val type = object : TypeToken<List<LibraryInfo?>?>() {}.type
//    List<LibraryInfo> list = gson.fromJson(BaseApplication.getApp().getString(R.string.library_json), type);

//    result.onResult(new ApiResponse(list, new ResponseStatus()));
    }


    fun downloadFile(downloadFile: DownloadFile, result: ApiResponse.Result<DownloadFile?>?) {
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                if (downloadFile.progress < 100) {
                    downloadFile.progress = downloadFile.progress + 1
                    Log.d("TAG", "下载进度 " + downloadFile.progress + "%")
                } else {
                    timer.cancel()
                }
                if (downloadFile.isForgive) {
                    timer.cancel()
                    downloadFile.progress = 0
                    downloadFile.isForgive = false
                    return
                }
                //        result.onResult(new ApiResponse(downloadFile, new ResponseStatus()));
            }
        }
        timer.schedule(task, 100, 100)
    }
}