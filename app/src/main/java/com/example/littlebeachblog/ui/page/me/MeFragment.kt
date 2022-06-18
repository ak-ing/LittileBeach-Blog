package com.example.littlebeachblog.ui.page.me

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.*
import com.example.littlebeachblog.databinding.FragmentMeBinding
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.ui.page.me.detailAc.*
import com.example.littlebeachblog.ui.state.MeViewModel
import com.example.littlebeachblog.ui.view.listener.AppBarStateChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ImmersionBar


class MeFragment : BaseFragment() {

    private lateinit var animationShow: Animation  //进入动画
    private lateinit var animationHide: Animation //退出动画
    private lateinit var mEvent: SharedViewModel
    private val mState: MeViewModel by viewModels()

    override fun initViewModel() {
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
        animationShow = AnimationUtils.loadAnimation(context, R.anim.toolbar_slide_in)
        animationHide = AnimationUtils.loadAnimation(context, R.anim.toolbar_slide_out)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_me, BR.vm, mState)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = binding as FragmentMeBinding
        /**
         * 初始化事件
         */
        binding.root.post { initEvent(bind) }

        mEvent.userInfo.observe(viewLifecycleOwner) {
            mState.logoutBtnVisibility.set(it != null)
            if (it != null) {
                mState.userInfo.value = it
            } else {

            }
        }
    }

    private fun initEvent(bind: FragmentMeBinding) {
        ImmersionBar.with(this).titleBar(bind.toolbar).statusBarDarkFont(true, 0.2f).init()
        animationShow.onAnimationStart { bind.toolbarContent.visibility = View.VISIBLE }
        animationHide.onAnimationEnd { bind.toolbarContent.visibility = View.GONE }

        /**
         * ToolBar变换
         */
        val cardHeight = bind.cardGroup.height.toFloat() - bind.toolbar.height      //这里toolBar会固定在顶部
        bind.appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, offset: Int, state: State) {
                bind.dragCoordinator.setToolBarState(state)
                val alpha = (cardHeight + offset) / cardHeight
                bind.cardGroup.alpha = alpha
                bind.toolbar.alpha = 1 - alpha
                if (state >= State.IDLE) toolbarSlideShow(bind.toolbarContent)     // -> 显示信息
                if (state <= State.SNAP) toolbarSlideHide(bind.toolbarContent)          //  -> 隐藏
            }
        })

        /**
         * 背景图变换
         */
        with(bind.dragCoordinator) {
            setOnAlphaChangeListener { bind.ivBg.alpha = it }
            viewLifecycleOwner.lifecycle.addObserver(this)
            setOnReleaseListener {
                val bundle = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair.create(bind.ivBg, "bg"), Pair.create(bind.cardGroup, "card")
                ).toBundle()
                startActivity(
                    Intent(activity, PersonDetailActivity::class.java).putExtra(
                        "userId",
                        mEvent.userInfo.value?._id
                    ),
                    bundle
                )
            }
        }
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
        if (view.visibility != View.GONE && (!animationHide.hasStarted() || animationHide.hasEnded())) view.startAnimation(
            animationHide
        )
    }

    /**
     * 判断是否已登录 已登录 -> true ， 否则 -> null
     */
    fun loginOrNull() = if (mEvent.token.value.isNullOrEmpty()) {
        "用户未登录".snackBar(binding.root.findViewById(R.id.drag_coordinator)).setAction("去登陆") {
            mActivity.goA(LoginActivity::class.java)
        }.show()
        null
    } else true

    inner class ClickProxy {

        /**
         * 点击大头像
         */
        fun clickAvatarBig() {
            if (mEvent.token.value.isNullOrEmpty()) {
                mActivity.goA(LoginActivity::class.java)
            } else {
                val bind = binding as FragmentMeBinding
                val bundle = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair.create(bind.ivBg, "bg"), Pair.create(bind.cardGroup, "card")
                ).toBundle()
                startActivity(
                    Intent(activity, PersonDetailActivity::class.java).putExtra(
                        "userId",
                        mEvent.userInfo.value?._id
                    ),
                    bundle
                )
            }
        }

        /**
         * 点击小头像
         */
        fun clickAvatarSmall() {
            if (mEvent.token.value.isNullOrEmpty()) {
                mActivity.goA(LoginActivity::class.java)
            } else {
                val bind = binding as FragmentMeBinding
                val bundle = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair.create(bind.ivAvatarSmall, "avatar"), Pair.create(bind.tvNickSmall, "nick")
                ).toBundle()
                startActivity(
                    Intent(activity, PersonDetailActivity::class.java).putExtra(
                        "userId",
                        mEvent.userInfo.value?._id
                    ),
                    bundle
                )
            }
        }

        /**
         * 播放历史
         */
        fun playHistory() {
            mActivity.goA(PlayHistoryActivity::class.java)
        }

        /**
         * 积分记录
         */
        fun scoreRecord() {
            loginOrNull() ?: return
            mActivity.goA(ScoreHistoryActivity::class.java)
        }

        /**
         * 修改密码
         */
        fun changePassword() {
            loginOrNull() ?: return
            mActivity.goA(PasswordActivity::class.java)
        }

        /**
         * 意见反馈
         */
        fun feedback() {
            loginOrNull() ?: return
            "请将您的建议通过邮件发送到:1450232082@qq.com".snackBar(binding.root.findViewById(R.id.drag_coordinator))
                .setDuration(Snackbar.LENGTH_LONG).show()
        }

        /**
         * 关于
         */
        fun about() {
            mActivity.goA(AboutActivity::class.java)
        }

        /**
         * 退出登录
         */
        fun logout() {
            MaterialAlertDialogBuilder(mActivity).setTitle("确定要退出登录吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { _, _ ->
                    App.getApp().viewModelStore.clear()
                    getSp("app").edit { remove("token") }
                    mActivity.goA(LoginActivity::class.java)
                    activity?.finish()
                }.show()
        }

    }

}