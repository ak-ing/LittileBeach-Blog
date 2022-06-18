package com.example.littlebeachblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.LikeNoticeBean
import com.example.littlebeachblog.databinding.ItemNoticeBinding
import com.example.littlebeachblog.ui.view.badge.Badge
import com.example.littlebeachblog.ui.view.badge.DragState
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder

/**
 * Created by AK on 2022/2/22 9:22.
 * God bless my code!
 */
class LikeNoticeAdapter : DataBindingListAdapter<LikeNoticeBean.LikesBean>(LikeNoticeDIFF) {

    private lateinit var readed: (like: LikeNoticeBean.LikesBean) -> Unit

    fun setReadListener(block: (LikeNoticeBean.LikesBean) -> Unit) {
        this.readed = block
    }

    object LikeNoticeDIFF : DiffUtil.ItemCallback<LikeNoticeBean.LikesBean>() {
        override fun areItemsTheSame(
            oldItem: LikeNoticeBean.LikesBean,
            newItem: LikeNoticeBean.LikesBean
        ): Boolean {
            return oldItem._id == newItem._id && oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: LikeNoticeBean.LikesBean,
            newItem: LikeNoticeBean.LikesBean
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeViewHolder<LikeNoticeBean.LikesBean> {
        val inflate = DataBindingUtil.inflate<ItemNoticeBinding>(
            LayoutInflater.from(parent.context),
            getLayoutId(),
            parent,
            false
        )
        return NoticeViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<LikeNoticeBean.LikesBean>, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        Log.d("TAG", "onBindViewHolder: ${item.type}")

        if (holder is NoticeViewHolder) {
            holder.badgeView.setBadgeCount(item.type)
            holder.badgeView.setOnDragStateChangedListener(object : Badge.OnDragStateChangedListener {
                override fun onDragStateChanged(dragState: DragState, badge: Badge, targetView: View) {
                    if (dragState == DragState.STATE_SUCCEED && this@LikeNoticeAdapter::readed.isInitialized) {
                        readed(item)
                    }
                }
            })
            holder.binding.executePendingBindings()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.item_notice
    }
}