package com.example.littlebeachblog.ui.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.ScoreBean
import com.example.littlebeachblog.databinding.ItemScoreHistoryBinding
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder

/**
 * Created by AK on 2022/2/27 14:05.
 * God bless my code!
 */
class ScoreHisAdapter : DataBindingListAdapter<ScoreBean>(ScoreDIFF) {
    object ScoreDIFF : DiffUtil.ItemCallback<ScoreBean>() {
        override fun areItemsTheSame(oldItem: ScoreBean, newItem: ScoreBean): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: ScoreBean, newItem: ScoreBean): Boolean {
            return oldItem == newItem
        }

    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["scoreType"], requireAll = false)
        fun setScoreType(tv: TextView, type: Int) {
            tv.text = when (type) {
                0 -> "用户注册奖励"
                else -> "系统奖励"
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["scoreDescribe"], requireAll = false)
        fun setScoreDescribe(tv: TextView, describe: String) {
            tv.text = describe
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.item_score_history
    }
}