package com.example.littlebeachblog.ui.adapter

import android.view.Gravity
import com.example.littlebeachblog.databinding.ItemNoticeBinding
import com.example.littlebeachblog.ui.view.badge.BadgeView
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder


/**
 * Created by AK on 2022/2/22 11:12.
 * God bless my code!
 */
class NoticeViewHolder<T>(bind: ItemNoticeBinding) : DataBindingViewHolder<T>(bind) {
    val badgeView = BadgeView(bind.root.context)
        .setBadgeGravity(Gravity.END or Gravity.BOTTOM)
        .setGravityOffset(12f, 8f, true)
        .bindTargetView(bind.rootView)
}