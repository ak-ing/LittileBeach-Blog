package com.example.littlebeachblog.ui.page.music

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.getSp
import com.example.littlebeachblog.app.utils.roomDir
import com.example.littlebeachblog.data.room.AppDataBase
import com.example.littlebeachblog.data.room.PlayHistoryEntity
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.player.PlayerManager
import com.example.littlebeachblog.player.contract.IMusicPlayCallBack
import com.example.littlebeachblog.ui.adapter.MusicListAdapter
import com.example.littlebeachblog.ui.state.MusicViewModel
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicFragment : BaseFragment() {
    private val mState: MusicViewModel by viewModels()
    private lateinit var mEvent: SharedViewModel

    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_music, BR.vm, mState)
            .addBindingParam(BR.click, MusicClickProxy())
            .addBindingParam(BR.adapter, MusicListAdapter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PlayerManager.getInstance().changeMusicEvent.observe(viewLifecycleOwner) {
            mState.notifyCurrentListChanged.value = true
        }

        mEvent.musicRequest.getFreeMusicsLiveData().observe(viewLifecycleOwner) {
            val result = it ?: return@observe
            val data = result.songs
            if (!data.isNullOrEmpty()) {
                mState.list.value = data
                if (PlayerManager.getInstance().album == null) {
                    PlayerManager.getInstance().loadAlbum(result)
                }
            }
        }
    }

    class MusicClickProxy {

        fun search() {}
    }
}