package com.example.littlebeachblog.ui.adapter

import android.graphics.Color
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.data.bean.TestAlbum
import com.example.littlebeachblog.databinding.ItemMusicBinding
import com.example.littlebeachblog.player.PlayerManager
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder

/**
 * Created by AK on 2022/2/16 23:01.
 * God bless my code!
 */
class MusicListAdapter : DataBindingListAdapter<TestAlbum.TestMusic>(MusicDiff) {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["artistName"], requireAll = false)
        fun artistNameParse(tv: TextView, item: TestAlbum.TestMusic) {
            val names = StringBuilder()
            item.ar.forEachIndexed { index, arBean ->
                names.append(arBean.name)
                if (index < item.ar.size - 1) {
                    names.append("/")
                }
            }
            tv.text = names
        }
    }

    init {
        setOnItemClickListener { bind, item, index ->
            PlayerManager.getInstance().playAudio(index)
        }
    }

    object MusicDiff : DiffUtil.ItemCallback<TestAlbum.TestMusic>() {
        override fun areItemsTheSame(oldItem: TestAlbum.TestMusic, newItem: TestAlbum.TestMusic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TestAlbum.TestMusic, newItem: TestAlbum.TestMusic): Boolean {
            return oldItem.equals(newItem)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: DataBindingViewHolder<TestAlbum.TestMusic>, position: Int) {
        super.onBindViewHolder(holder, position)
        val bind = holder.binding as ItemMusicBinding
        val index = PlayerManager.getInstance().albumIndex
        bind.ivPlayStatus.setColor(if (index == position) holder.itemView.context.getColor(R.color.gray_dark) else Color.TRANSPARENT)
    }

    override fun getLayoutId(): Int {
        return R.layout.item_music
    }
}