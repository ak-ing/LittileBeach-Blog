package com.example.littlebeachblog.ui.page.home.home_page_child

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.*
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.FooterAdapter
import com.example.littlebeachblog.ui.adapter.MoYvListAdapter
import com.example.littlebeachblog.ui.page.home.FishDetailActivity
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.ui.state.home_state_child.MoYvViewModel
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGAImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by AK on 2022/1/31 17:04.
 * God bless my code!
 */
class MoYvFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(uId: String?): MoYvFragment {
            val ft = MoYvFragment()
            ft.arguments = bundleOf("userId" to uId)
            return ft
        }
    }

    private val mState: MoYvViewModel by viewModels()
    private lateinit var mEvent: SharedViewModel
    private val moYvAdapter = MoYvListAdapter()
    private var uId: String? = null

    override fun initViewModel() {
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_moyv, BR.vm, mState)
            .addBindingParam(BR.adapter, moYvAdapter.withLoadStateFooter(FooterAdapter { moYvAdapter.retry() }))
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        initUILoader(R.id.rv_fish)
        return root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uId = arguments?.getString("userId")
        /**
         * 点击重新加载
         */
        uiLoader.setOnRetryClickListener { moYvAdapter.retry() }

        mState.refreshStateChanged.value = true
        moYvAdapter.addLoadStateListener {
            mState.refreshStateChanged.value = true
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    if (moYvAdapter.itemCount == 0) uiLoader.empty() else
                        uiLoader.show()
                }
                is LoadState.Loading -> {
                    uiLoader.loading()
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    state.error.printStackTrace()
                    uiLoader.error()
                }
            }
        }

        getMoYvs()

        /**
         * 发布摸鱼后刷新
         */
        mEvent.moYvRefresh.observe(viewLifecycleOwner) {
            if (it) {
                moYvAdapter.refresh()
                Toast.makeText(context, "摸鱼成功", Toast.LENGTH_SHORT).show()
                mEvent.moYvRefresh.value = false
            }
        }

        /**
         * 点赞动画弹窗
         */
        val pop = LayoutInflater.from(requireContext()).inflate(R.layout.pop_like, null, false)
        val svg = pop.findViewById<SVGAImageView>(R.id.svg_like)
        val likePop = PopupWindow(pop, WRAP_CONTENT, WRAP_CONTENT)
        svg.callback = object : SVGACallback {
            override fun onFinished() {
                likePop.dismiss()
            }

            override fun onPause() {
            }

            override fun onRepeat() {
            }

            override fun onStep(frame: Int, percentage: Double) {
            }
        }

        /**
         * 跳转摸鱼详情页
         */
        moYvAdapter.setOnItemClickListener { bind, item, index ->
            mEvent.fishItem.value = item
            enterTransition = Explode()
            exitTransition = Explode()
            val bundle =
                ActivityOptions.makeSceneTransitionAnimation(activity, bind.root, "fishItem").toBundle()
            startActivity(
                Intent(activity, FishDetailActivity::class.java).putExtra("animation", "pair"),
                bundle
            )
        }

        /**
         * 摸鱼点赞
         */
        moYvAdapter.doLike = { fish, v ->
            if (mEvent.token.value.isNullOrEmpty()) {
                "用户未登录".snackBar(binding.root).setAction("去登陆") {
                    mActivity.goA(LoginActivity::class.java)
                }.show()
                false
            } else {
                mState.requestDoLike(fish._id, mapOf("userId" to "${fish.userId}"))
                if (!svg.isAnimating) svg.startAnimation()
                if (!likePop.isShowing)
                    likePop.showAsDropDown(v, 0, -requireContext().dp2px(100) - v.measuredHeight)
                true
            }
        }
    }

    /**
     * 获取摸鱼列表
     */
    private fun getMoYvs() {
        lifecycleScope.launch {
            uiLoader.updateStatus(UILoader.UIStatus.LOADING)
            mState.getMoYvData().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    if (uId.isNullOrEmpty()) {
                        moYvAdapter.submitData(it)
                    } else {
                        moYvAdapter.submitData(it.filter { it.userId == uId })
                    }
                }
        }
    }

    inner class ClickProxy {

        /**
         * 下拉刷新
         */
        fun refresh() = moYvAdapter.refresh()

    }

}