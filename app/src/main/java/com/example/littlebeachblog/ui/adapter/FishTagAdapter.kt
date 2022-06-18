package com.example.littlebeachblog.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.FishTagBean
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter

/**
 * Created by AK on 2022/2/11 15:31.
 * God bless my code!
 */
class FishTagAdapter : DataBindingListAdapter<FishTagBean>(FishTagDiff) {
    object FishTagDiff : DiffUtil.ItemCallback<FishTagBean>() {
        override fun areItemsTheSame(oldItem: FishTagBean, newItem: FishTagBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FishTagBean, newItem: FishTagBean): Boolean {
            return oldItem == newItem
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.item_fish_tag
    }
}