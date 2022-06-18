package com.example.littlebeachblog.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.AvatarBean
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder

/**
 * Created by AK on 2022/3/2 9:34.
 * God bless my code!
 */
class AvatarRecommendAdapter : DataBindingListAdapter<AvatarBean>(AvatarDIFF) {
    object AvatarDIFF : DiffUtil.ItemCallback<AvatarBean>() {
        override fun areItemsTheSame(oldItem: AvatarBean, newItem: AvatarBean): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: AvatarBean, newItem: AvatarBean): Boolean {
            return oldItem == newItem
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.item_avatar_recommend
    }
}