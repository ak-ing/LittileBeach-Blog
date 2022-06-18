package com.example.littlebeachblog.ui.state.home_state_child

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.FishTagBean
import com.example.littlebeachblog.data.bean.ImageBean
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.config.ApiResponse
import com.example.littlebeachblog.data.repository.HomeRepository
import com.example.littlebeachblog.data.repository.UtilRepository
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MoYvViewModel : ViewModel() {

    val refreshStateChanged = MutableLiveData<Boolean>()

    val putBtnState = ObservableBoolean(false)

    val putBtnColor = ObservableInt(R.color.color_search)

    val contentLd = MutableLiveData<String>()

    val textCount = MutableLiveData("0/512")

    val fishTagLd = MutableLiveData<ApiResponse<List<FishTagBean>>>()

    val imageLd = MutableLiveData<ApiResponse<List<ImageBean>>>()

    val putFishLd = MutableLiveData<ApiResponse<String>>()

    val fishTag = MutableLiveData<FishTagBean>()

    /**
     * 获取摸鱼动态列表
     */
    fun getMoYvData(): Flow<PagingData<MoYv>> {
        return HomeRepository.getInstance().getMoYvs().cachedIn(viewModelScope)
    }

    /**
     * 获取摸鱼话题列表
     */
    fun getFishTags() {
        viewModelScope.launch(Dispatchers.IO) {
            HomeRepository.getInstance().getFishTags(fishTagLd::postValue)
        }
    }

    /**
     *  发布摸鱼
     */
    fun putFish(token: String, fish: MoYv) {
        viewModelScope.launch(Dispatchers.IO) {
            HomeRepository.getInstance().putFish(token, fish, putFishLd::postValue)
        }
    }

    /**
     * 上传图片
     */
    fun uploadImg(token: String, image: List<MultipartBody.Part>) = viewModelScope.launch(Dispatchers.IO) {
        UtilRepository.getInstance().uploadImg(token, image, imageLd::postValue)
    }

    /**
     * 摸鱼点赞
     */
    fun requestDoLike(fishId: String, body: Map<String, String>) = viewModelScope.launch(Dispatchers.IO) {
        HomeRepository.getInstance().doLikeFish(fishId, body)
    }
}