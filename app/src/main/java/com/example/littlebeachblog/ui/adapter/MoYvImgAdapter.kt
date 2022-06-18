package com.example.littlebeachblog.ui.adapter

import android.util.Log
import android.view.RoundedCorner
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.utils.dp2px
import com.example.littlebeachblog.data.config.GlideEngine
import com.example.littlebeachblog.databinding.ItemMoyvImgAddBinding
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.luck.picture.lib.entity.LocalMedia


/**
 * Created by AK on 2022/2/10 17:01.
 * God bless my code!
 */
class MoYvImgAdapter : DataBindingListAdapter<LocalMedia>(MoYvImgDiff) {
    lateinit var addClick: () -> Unit
    lateinit var itemClick: (position: Int) -> Unit


    companion object {
        @JvmStatic
        @BindingAdapter(value = ["moYvImgPath"], requireAll = false)
        fun setImgPath(iv: ImageView, path: String?) {
            if (path.isNullOrEmpty()) return
            Glide.with(iv).load(path).transform(RoundedCorners(iv.context.dp2px(4)))
                .error(R.drawable.ps_image_placeholder)
                .into(iv)
        }
    }

    object MoYvImgDiff : DiffUtil.ItemCallback<LocalMedia>() {
        override fun areItemsTheSame(oldItem: LocalMedia, newItem: LocalMedia): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocalMedia, newItem: LocalMedia): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<LocalMedia>, position: Int) {
        val img = initSize(holder)
        if (position == itemCount - 1) {
            img.setBackgroundResource(R.mipmap.ic_add_image)
            holder.itemView.setOnClickListener { if (this::addClick.isInitialized) addClick() }
        } else {
            holder.itemView.setOnClickListener { if (this::itemClick.isInitialized) itemClick(position) }
            super.onBindViewHolder(holder, position)
        }
    }

    private fun initSize(holder: DataBindingViewHolder<LocalMedia>): ShapeableImageView {
        val bind = holder.binding as ItemMoyvImgAddBinding
        val img = bind.imgFish
        img.post {
            val width = img.width
            val lp = img.layoutParams
            lp.height = width
            img.layoutParams = lp
        }
        return img
    }

    override fun getLayoutId(): Int {
        return R.layout.item_moyv_img_add
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }
}