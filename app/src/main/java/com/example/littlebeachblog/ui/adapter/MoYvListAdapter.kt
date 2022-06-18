package com.example.littlebeachblog.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.*
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.utils.BASE_URL
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.databinding.ItemMoyvBinding
import com.example.littlebeachblog.ui.binding_adapter.CommonBindingAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingPagingAdapter
import com.example.littlebeachblog.ui.view.recycler_adapter.DataBindingViewHolder
import net.mikaelzero.mojito.ext.mojito
import net.mikaelzero.mojito.impl.DefaultPercentProgress
import net.mikaelzero.mojito.impl.NumIndicator
import org.json.JSONArray

/**
 * Created by AK on 2022/2/4 14:57.
 * God bless my code!
 */
class MoYvListAdapter : DataBindingPagingAdapter<MoYv>(DiffUtils) {
    object DiffUtils : DiffUtil.ItemCallback<MoYv>() {
        override fun areItemsTheSame(oldItem: MoYv, newItem: MoYv): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: MoYv, newItem: MoYv): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var doLike: (fish: MoYv, v: View) -> Boolean
    lateinit var doComment: (fish: MoYv) -> Boolean

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["isLike"], requireAll = false)
        fun isLike(iv: ImageView, like: Boolean) {
            iv.setImageResource(if (like) R.mipmap.ic_liked_blue else R.mipmap.icon_zan_grey_feidian3)
        }

        @JvmStatic
        @BindingAdapter(value = ["likeNum"], requireAll = false)
        fun likeNum(tv: TextView, likeNum: Int) {
            tv.text = if (likeNum == 0) "点赞" else "$likeNum"
        }

        @JvmStatic
        @BindingAdapter(value = ["fishImgParse", "fishImgEnable"], requireAll = false)
        fun setFishImgParse(rv: RecyclerView, fish: MoYv?, isClick: Boolean?) {
            if (fish == null) return
            val str = fish.images ?: return
            val images = JSONArray(str).optString(0).split(",")
            if (images.isNullOrEmpty()) return
            val spanCount = when (images.size) {
                1 -> 1
                2 -> 2
                else -> 3
            }
            val current = images.map { BASE_URL + it }
            //图片列表
            rv.run {
                layoutManager = GridLayoutManager(context, spanCount)
                adapter = FishItemImgAdapter(images.size).apply {
                    if (isClick != null && isClick) {
                        setOnItemClickListener { bind, item, index ->
                            mojito(R.id.img_fish) {     //画廊显示
                                urls(current)
                                position(index)
                                progressLoader { DefaultPercentProgress() } //进度加载器
                                setIndicator(NumIndicator())        //数字指示器
                            }
                        }
                    }
                    submitList(current)
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["fishTag"], requireAll = false)
        fun setFishTag(tv: TextView, tag: String?) {
            if (tag.isNullOrEmpty()) {
                tv.visibility = GONE
            } else {
                tv.visibility = VISIBLE
                tv.text = when (tag) {
                    "1384518752720130050" -> "划水摸鱼"
                    "1384518439661473794" -> "一语惊人"
                    "1384189862646657025" -> "代码之美"
                    "1384190025620533249" -> "养生之道"
                    "1384518339434385409" -> "求职&offer"
                    "1384518529818038273" -> "职场经验"
                    "1384518640040153089" -> "猿(媛)装备"
                    "1384518863412006914" -> "小目标"
                    "1388132146446655490" -> "段子笑话"
                    "1388132260582055938" -> "轮子推荐"
                    "1388132359030759425" -> "打工人日常"
                    "1388132524890316802" -> "今日头条"
                    "1388132608877060098" -> "今天学废了吗？"
                    "1388132715345272834" -> "人言否"
                    "1388132816037928962" -> "逼格满满"
                    "1388132927681912834" -> "工具效率"
                    "1388133357916839938" -> "一图胜利千言"
                    "1388133525621891073" -> "资料分享"
                    "1388133666609225730" -> "离职小技巧"
                    "1388133780996284417" -> "蹲坑读物"
                    "1388133893743370241" -> "掐指一算"
                    "1388134042825711617" -> "夜猫子"
                    "1388134192650444801" -> "心情碎碎念"
                    "1458039707895095298" -> "下班打卡"
                    else -> "其他"
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DataBindingViewHolder<MoYv>, position: Int) {
        super.onBindViewHolder(holder, position)
        val bind = holder.binding as ItemMoyvBinding
        bind.ivCoverMoyv.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                bind.root.performClick()
            }
            false
        }
        //点赞
        CommonBindingAdapter.onClickWithDebouncing(bind.btnLikeFish) {
            if (this::doLike.isInitialized) {
                getItem(position)?.let { item ->
                    if (doLike(item, bind.btnLikeFish)) {
                        isLike(bind.ivLike, true)
                    }
                }
            }
        }

        //评论
        CommonBindingAdapter.onClickWithDebouncing(bind.btnCommentFish) {
            if (this::doComment.isInitialized) {
                getItem(position)?.let { it1 -> doComment(it1) }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.item_moyv
    }
}