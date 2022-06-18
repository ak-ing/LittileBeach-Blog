package com.example.littlebeachblog.ui.state

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlebeachblog.data.bean.TestAlbum
import com.example.littlebeachblog.domain.request.MusicRequest

class MusicViewModel : ViewModel() {

    val initTabAndPage = MutableLiveData(0)

    val pageAssetPath = ObservableField("summary.html")

    val list: MutableLiveData<List<TestAlbum.TestMusic>> = MutableLiveData()

    val notifyCurrentListChanged = MutableLiveData<Boolean>()

    val musicRequest: MusicRequest = MusicRequest()
}