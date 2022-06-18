package com.example.littlebeachblog.ui.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.utils.dp2px
import com.example.littlebeachblog.data.config.GlideEngine
import com.example.littlebeachblog.databinding.ItemMoyvImgAddBinding
import com.example.littlebeachblog.databinding.ItemMoyvImgShowBinding
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingListAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
import net.mikaelzero.mojito.Mojito
import net.mikaelzero.mojito.ext.mojito

/**
 * Created by AK on 2022/2/13 14:54.
 * God bless my code!
 */
class FishItemImgAdapter(private val size: Int) : DataBindingListAdapter<String>(FishItemImgDiff) {
    object FishItemImgDiff : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<String>, position: Int) {
        super.onBindViewHolder(holder, position)
        when (size) {
            1 -> initSize(holder, 180)
            2 -> initSize(holder, 100)
            3, 4, 5, 6, 7, 8, 9 -> initSize(holder, null)
        }
    }

    private fun initSize(holder: DataBindingViewHolder<String>, height: Int?): ShapeableImageView {
        val bind = holder.binding as ItemMoyvImgShowBinding
        val img = bind.imgFish
        if (height != null) {
            img.post {
                val lp = img.layoutParams
                lp.height = img.context.dp2px(height)
                img.layoutParams = lp
                if (height == 100) img.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            return img
        }
        img.post {
            val width = img.width
            val lp = img.layoutParams
            lp.height = width
            img.layoutParams = lp
            img.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return img
    }

    override fun getLayoutId(): Int {
        return R.layout.item_moyv_img_show
    }
}