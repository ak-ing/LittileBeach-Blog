package com.example.littlebeachblog.ui.page.me.detailAc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.onAnimationEnd
import com.example.littlebeachblog.app.utils.onAnimationStart
import com.example.littlebeachblog.databinding.ActivityPersonDetailBinding
import com.example.littlebeachblog.databinding.FragmentMeBinding
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.PageAdapter
import com.example.littlebeachblog.ui.page.home.home_page_child.MoYvFragment
import com.example.littlebeachblog.ui.page.home.home_page_child.SubscriptionFragment
import com.example.littlebeachblog.ui.page.me.child_page.PersonFishFragment
import com.example.littlebeachblog.ui.page.me.child_page.PersonHomeFragment
import com.example.littlebeachblog.ui.state.MeViewModel
import com.example.littlebeachblog.ui.view.listener.AppBarStateChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.gyf.immersionbar.ImmersionBar

class PersonDetailActivity : BaseActivity() {

    private lateinit var animationShow: Animation  //进入动画
    private lateinit var animationHide: Animation //退出动画
    val mState: MeViewModel by viewModels()
    private val pageAdapter by lazy { PageAdapter(supportFragmentManager, lifecycle) }

    private lateinit var mEvent: SharedViewModel
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
        animationShow = AnimationUtils.loadAnimation(this, R.anim.toolbar_slide_in)
        animationHide = AnimationUtils.loadAnimation(this, R.anim.toolbar_slide_out)
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        val uId = intent.getStringExtra("userId")
        pageAdapter.setFragmentSet(listOf(PersonHomeFragment.newInstance(), MoYvFragment.newInstance(uId)))
        return DataBindingConfig(R.layout.activity_person_detail, BR.vm, mState)
            .addBindingParam(BR.click, ClickProxy())
            .addBindingParam(BR.adapter, pageAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "")
        mState.initTabAndPage.value = R.id.viewPager_person
        val bind = binding as ActivityPersonDetailBinding

        mEvent.userInfo.observe(this) {
            if (it != null) {
                mState.userInfo.value = it
            }
        }

        /**
         * 初始化事件
         */
        binding.root.post { initEvent(bind) }
    }

    private fun initEvent(bind: ActivityPersonDetailBinding) {
        animationShow.onAnimationStart { bind.title.visibility = View.VISIBLE }
        animationHide.onAnimationEnd { bind.title.visibility = View.INVISIBLE }

        bind.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, offset: Int, state: State) {
                if (state == State.COLLAPSED) {
                    toolbarSlideShow(bind.title)    // -> 显示信息
                    bind.tabLayout.setBackgroundResource(R.color.white)
                } else {
                    toolbarSlideHide(bind.title)       //  -> 隐藏
                    bind.tabLayout.setBackgroundResource(R.color.gray)
                }
            }
        })
    }

    /**
     * 移动动画 -> 显示
     */
    fun toolbarSlideShow(view: View) {
        if (view.visibility != View.VISIBLE) view.startAnimation(
            animationShow
        )
    }

    /**
     * 移动动画 -> 隐藏
     */
    fun toolbarSlideHide(view: View) {
        if (view.visibility != View.INVISIBLE && (!animationHide.hasStarted() || animationHide.hasEnded())) view.startAnimation(
            animationHide
        )
    }

    override fun onDestroy() {
        mState.initTabAndPage.value = 0
        super.onDestroy()
    }

    inner class ClickProxy {

    }
}