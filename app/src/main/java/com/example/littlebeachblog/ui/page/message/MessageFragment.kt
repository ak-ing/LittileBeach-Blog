package com.example.littlebeachblog.ui.page.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.*
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.domain.message.NoticeViewModel
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.LikeNoticeAdapter
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.ui.state.MessageViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*

class MessageFragment : BaseFragment() {
    private val mState: MessageViewModel by viewModels()
    private lateinit var mNotice: NoticeViewModel
    private lateinit var mEvent: SharedViewModel
    private lateinit var mUiLoader: UILoader

    private val likeNoticeAdapter = LikeNoticeAdapter()

    override fun initViewModel() {
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
        mNotice = getActivityScopeViewModel(NoticeViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_message, BR.vm, mState)
            .addBindingParam(BR.click, ClickProxy())
            .addBindingParam(BR.adapter, likeNoticeAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        mUiLoader = root!!.insertUiLoader(R.id.rv_notice)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY)
        mEvent.token.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) return@observe mUiLoader.updateStatus(UILoader.UIStatus.EMPTY)
            else mUiLoader.updateStatus(UILoader.UIStatus.LOADING)
            /**
             * 获取用户点赞通知
             */
            lifecycleScope.launch(Dispatchers.Main) {
                mNotice.mMsgNotice.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .flowOn(Dispatchers.IO).collect {
                        it.getOrNull() ?: mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR)
                        if (!it.isSuccess()) return@collect
                        if (it.getData().likes.isNotEmpty()) {
                            mState.likesFl.value = it.getData().likes
                            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS)
                        } else mUiLoader.updateStatus(UILoader.UIStatus.EMPTY)
                    }
            }

        }

        /**
         * 网路错误 -> 重新加载状态
         */
        mUiLoader.setOnRetryClickListener {
            if (mUiLoader.currentStatus == UILoader.UIStatus.NETWORK_ERROR) {
                mUiLoader.updateStatus(UILoader.UIStatus.LOADING)
            }
        }

        /**
         * 通知总数观察者 -> 拖拽一键已读
         */
        mNotice.msgNoticeLd.observe(viewLifecycleOwner) {
            if (it.getData().unReadCount == 0) {
                val list = mutableListOf<LikeNoticeBean.LikesBean>()
                for (like in mState.likesFl.value) {
                    list.add(like.clone().apply { type = 0 })
                }
                mState.likesFl.value = list
            }
        }

        /**
         * 单条已读监听
         */
        likeNoticeAdapter.setReadListener {
            mState.requestReadOneLike("${mEvent.token.value}", it._id)
            mNotice.msgNoticeLd.value?.let {
                it.getData().unReadCount -= 1
                mNotice.msgNoticeLd.value = it
            }
        }

    }

    inner class ClickProxy {

        /**
         * 一键已读点击事件
         */
        fun readAll() {
            if (!mEvent.token.value.isNullOrEmpty()) {
                MaterialAlertDialogBuilder(mActivity).setMessage("确定将消息列表置为已读吗？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->
                        mNotice.requestReadAllLike(mEvent.token.value!!)
                        val value = mNotice.msgNoticeLd.value
                        value?.let {
                            it.getData().unReadCount = 0
                            mNotice.msgNoticeLd.value = it
                        }
                        "一键已读".snackBar(binding.root).show()
                    }.show()
            } else "没有更多消息了~ o(╥﹏╥)o".snackBar(binding.root).show()
        }

    }

}