package com.example.littlebeachblog

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.ActivityController
import com.example.littlebeachblog.app.utils.goA
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.data.repository.UserRepository
import com.example.littlebeachblog.databinding.ActivityMainBinding
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.domain.message.NoticeViewModel
import com.example.littlebeachblog.player.PlayerManager
import com.example.littlebeachblog.ui.view.badge.Badge
import com.example.littlebeachblog.ui.view.badge.BadgeView
import com.example.littlebeachblog.ui.view.badge.DragState
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ImmersionBar
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private var lastTime: Long = 0
    private var mIsListened = false
    private lateinit var binding: ActivityMainBinding
    private val mNotice by viewModels<NoticeViewModel>()
    private val mEvent = ViewModelProvider(App.getApp()).get<SharedViewModel>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityController.addActivity(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding.navView.setOnNavigationItemSelectedListener {
            if (binding.slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
            navHostFragment.findNavController().navigate(it.itemId)
            true
        }

        //????????????
        val badgeView = BadgeView(this@MainActivity)
            .bindTargetView(binding.navView.findViewById(R.id.navigation_message))

        /**
         * ?????????????????????
         */
        mEvent.token.observe(this) {
            if (it.isNullOrEmpty()) return@observe
            Log.d("TAG", "login------------: $it")
            App.getApp().mMap["token"] = it

            /**
             * ??????????????????
             */
            lifecycleScope.launch(Dispatchers.IO) {
                val result = UserRepository.getInstance().getUserInfo(it).getOrNull() ?: return@launch
                result.getData()?.let {
                    mEvent.userInfo.postValue(it)
                }
                if (result.getCode() == 401) {
                    delay(2000)
                    "????????????".snackBar(binding.root).setAction("?????????") {
                        goA(LoginActivity::class.java)
                    }.setDuration(Snackbar.LENGTH_LONG).show()
                }
            }

            /**
             * ??????????????????
             */
            badgeView.setOnDragStateChangedListener(object : Badge.OnDragStateChangedListener {
                override fun onDragStateChanged(dragState: DragState, badge: Badge, targetView: View) {
                    if (dragState == DragState.STATE_SUCCEED) {
                        val value = mNotice.msgNoticeLd.value   //??????????????????
                        value?.let {
                            it.getData().unReadCount = 0
                            mNotice.msgNoticeLd.value = it
                        }
                        "????????????".snackBar(binding.root).show()
                        mNotice.requestReadAllLike(it)
                    }
                }
            })

            /**
             * ????????????????????????
             */
            lifecycleScope.launch(Dispatchers.Main) {
                mNotice.mMsgNotice.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .flowOn(Dispatchers.IO).collect {
                        if (!it.isSuccess()) return@collect
                        if (badgeView.getBadgeCount() == it.getData().unReadCount) return@collect
                        mNotice.msgNoticeLd.value = it
                    }
            }
        }

        /**
         * ?????????????????????
         */
        mNotice.msgNoticeLd.observe(this) {
            badgeView.setBadgeCount(it.getData().unReadCount)
        }

        //???????????????????????????
        requestMusicRecommend()
    }

    /**
     * ??????????????????
     */
    private fun requestMusicRecommend() {
        if (PlayerManager.getInstance().album == null) {
            lifecycleScope.launch(Dispatchers.IO) { mEvent.musicRequest.requestFreeMusics() }
        } else {
            mEvent.musicRequest.getFreeMusicsLiveData().value = PlayerManager.getInstance().album
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!mIsListened) {
            mEvent.toAddSlideListener.value = true
            mIsListened = true
        }
    }

    override fun onBackPressed() {
        if (binding.slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return
        }
        if (binding.navView.selectedItemId != R.id.navigation_home) {
            binding.navView.selectedItemId = R.id.navigation_home
        }
        if (System.currentTimeMillis() - lastTime > 2000) {
            lastTime = System.currentTimeMillis()
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show()
            return
        } else {
            finish()
        }

    }


}