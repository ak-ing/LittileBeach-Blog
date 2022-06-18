package com.example.littlebeachblog.ui.page.me.detailAc

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.roomDir
import com.example.littlebeachblog.data.room.AppDataBase
import com.example.littlebeachblog.databinding.ActivityPlayHistoryBinding
import com.example.littlebeachblog.ui.adapter.PlayHistoryAdapter
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayHistoryActivity : BaseActivity() {
    private val mAdapter = PlayHistoryAdapter()
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_play_history)
            .addBindingParam(BR.adapter, mAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = binding as ActivityPlayHistoryBinding
        initToolbar(R.id.toolbar, "播放历史")

        lifecycleScope.launch(Dispatchers.IO) {
            val dir = filesDir.path + roomDir
            val db = Room.databaseBuilder(this@PlayHistoryActivity, AppDataBase::class.java, dir).build()
            val playHistory = db.playHistoryDao().getAll().reversed()
            withContext(Dispatchers.Main) {
                mAdapter.submitList(playHistory)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}