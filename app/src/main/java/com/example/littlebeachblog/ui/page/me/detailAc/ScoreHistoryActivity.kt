package com.example.littlebeachblog.ui.page.me.detailAc

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.ui.adapter.ScoreHisAdapter
import com.example.littlebeachblog.ui.state.ScoreViewModel
import com.gyf.immersionbar.ImmersionBar

class ScoreHistoryActivity : BaseActivity() {
    val mState by viewModels<ScoreViewModel>()
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_score_history, BR.vm, mState)
            .addBindingParam(BR.adapter, ScoreHisAdapter())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "积分记录")
        initUILoader(R.id.rv_score)

        uiLoader.loading()
        mState.getScoreResponse().observe(this) {
            uiLoader.with(it) ?: return@observe
            mState.scoreListLd.value = it.getData()
        }

        mState.requestScoreList()
    }

}