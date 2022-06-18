package com.example.littlebeachblog.domain.request

import androidx.lifecycle.MutableLiveData
import com.example.littlebeachblog.data.bean.TestAlbum
import com.example.littlebeachblog.data.repository.MusicRepository

/**
 * Created by AK on 2022/2/16 20:22.
 * God bless my code!
 */
class MusicRequest : BaseRequest() {

    private val mFreeMusicsLiveData = MutableLiveData<TestAlbum>()

    fun getFreeMusicsLiveData(): MutableLiveData<TestAlbum> {
        return mFreeMusicsLiveData
    }

    suspend fun requestFreeMusics() {
        MusicRepository.getInstance().getFreeMusic(mFreeMusicsLiveData::postValue)
    }

}