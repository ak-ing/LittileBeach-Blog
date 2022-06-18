package com.example.littlebeachblog.ui.page.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.state.home_state_child.MoYvViewModel
import com.gyf.immersionbar.ImmersionBar

class FishDetailActivity : BaseActivity() {
    private val mState by viewModels<MoYvViewModel>()
    private lateinit var mEvent: SharedViewModel

    override fun initViewModel() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_fish_detail, BR.vm, mState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "摸鱼详情")
        ViewCompat.setTransitionName(binding.root, "fishItem")
        mEvent.fishItem.observe(this) {
            binding.setVariable(BR.item, it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }

}