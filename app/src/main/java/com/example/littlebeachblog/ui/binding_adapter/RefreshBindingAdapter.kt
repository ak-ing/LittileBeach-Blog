package com.example.littlebeachblog.ui.binding_adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.utils.BASE_URL
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by AK on 2022/2/7 18:43.
 * God bless my code!
 */

@BindingAdapter(value = ["parseLabel"], requireAll = false)
fun parseLabel(tv: TextView, label: String?) {
    tv.text = if (label.isNullOrEmpty()) "不是懒得签名！只是在思考" else label
}

@BindingAdapter(value = ["parseSex"], requireAll = false)
fun parseSex(tv: TextView, sex: Int) {
    tv.text = when (sex) {
        0 -> "女"
        1 -> "男"
        else -> "未知QAQ"
    }
}

@BindingAdapter(value = ["commonImgUrl"], requireAll = false)
fun setImgUrl(img: ImageView, url: String) {
    Glide.with(img).load(url).error(R.mipmap.picture_icon_placeholder).into(img)
}

@BindingAdapter(value = ["refreshListener"], requireAll = false)
fun addRefreshListener(layout: SmartRefreshLayout, block: () -> Unit) {
    layout.setOnRefreshListener { block() }
}

@BindingAdapter(value = ["refreshStateOver"], requireAll = false)
fun addRefreshStateOver(refresh: SmartRefreshLayout, stateOver: Boolean) {
    if (!stateOver) return
    if (refresh.isRefreshing) refresh.finishRefresh()
}

@BindingAdapter(value = ["lottieStateListener"], requireAll = false)
fun addLottieStateListener(lottie: LottieAnimationView, loadState: Boolean) {
    lottie.run {
        if (loadState) {
            pauseAnimation()
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            playAnimation()
        }
    }
}

@BindingAdapter(value = ["playerTimeParse"], requireAll = false)
fun playerTimeParse(tv: TextView, number: Int) {
    val time = SimpleDateFormat("mm:ss", Locale.getDefault()).format(number)
    tv.text = time ?: ""
}

/**
 * Eamil监听加载头像加载头像
 */
@BindingAdapter(value = ["avatarEmail", "avatarLoadListener"], requireAll = false)
fun loadAvatar(view: ImageView, email: String?, clickProxy: LoginActivity.LoginClickProxy) {
    if (email == null) {
        view.visibility = View.INVISIBLE
        return
    }
    val imgUrl: String = "$BASE_URL/user/avatar/$email"
    Log.d("TAG", "loadAvatar: $imgUrl")
    Glide.with(view.context).load(imgUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?, model: Any, target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                clickProxy.onAvatarLoad(false)
                view.visibility = View.INVISIBLE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any, target: Target<Drawable?>,
                dataSource: DataSource, isFirstResource: Boolean
            ): Boolean {
                view.setImageDrawable(resource)
                clickProxy.onAvatarLoad(true)
                view.visibility = View.VISIBLE
                return false
            }
        }).into(view)
}