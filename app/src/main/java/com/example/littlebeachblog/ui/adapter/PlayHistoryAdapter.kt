package com.example.littlebeachblog.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.room.PlayHistoryEntity
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter

/**
 * Created by AK on 2022/2/26 19:36.
 * God bless my code!
 */
class PlayHistoryAdapter : DataBindingListAdapter<PlayHistoryEntity>(PlayHistoryDIFF) {
    object PlayHistoryDIFF : DiffUtil.ItemCallback<PlayHistoryEntity>() {
        override fun areItemsTheSame(oldItem: PlayHistoryEntity, newItem: PlayHistoryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayHistoryEntity, newItem: PlayHistoryEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.item_play_history
    }
}