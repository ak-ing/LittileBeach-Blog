package com.example.littlebeachblog.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.littlebeachblog.app.App
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern
import android.text.TextUtils
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import com.example.littlebeachblog.app.BaseActivity
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by AK on 2022/2/5 10:30.
 * God bless my code!
 */

/**
 * 在successView的上一级插入UILoader,并把successView添加到Loader
 * 返回UiLoader
 */
fun View.insertUiLoader(successViewId: Int): UILoader {
    val successView = findViewById<View>(successViewId)
    val container = successView.parent as ViewGroup
    container.removeView(successView)
    val loader = object : UILoader(context) {
        override fun getSuccessView(container: ViewGroup): View {
            return successView
        }
    }
    container.addView(loader)
    return loader
}

/**
 * 动画启动回调
 */
fun Animation.onAnimationStart(block: (anim: Animation) -> Unit) =
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            block(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    })

/**
 * 动画结束回调
 */
fun Animation.onAnimationEnd(block: (anim: Animation) -> Unit) =
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            block(animation)
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    })

/**
 * dp转换
 */
@JvmName("dp2px")
fun Context.dp2px(dp: Int): Int {
    return (dp * resources.displayMetrics.density + 0.5).toInt()
}

fun Context.px2dp(px: Int): Int {
    return (px / this.resources.displayMetrics.density + 0.5).toInt()
}

fun dpToPx(dp: Int) = (dp * App.getApp().resources.displayMetrics.density + 0.5).toInt()

/**
 * 吐司
 */
var mToast = Toast.makeText(App.getApp().applicationContext, "", Toast.LENGTH_SHORT)

fun String.show() {
    mToast.cancel()
    mToast = Toast.makeText(App.getApp().applicationContext, this, Toast.LENGTH_SHORT)
    mToast.show()
}

fun String.showLong() {
    mToast.cancel()
    mToast = Toast.makeText(App.getApp().applicationContext, this, Toast.LENGTH_LONG)
    mToast.show()
}

/**
 * 获取token
 */
fun Context.getToken(): String = getSp("app").getString("token", "").toString()

/**
 * md5加密
 */
fun String.md5(): String? {
    if (TextUtils.isEmpty(this)) {
        return ""
    }
    var md5: MessageDigest? = null
    try {
        md5 = MessageDigest.getInstance("MD5")
        val bytes: ByteArray = md5.digest(this.toByteArray())
        var result: String? = ""
        for (b in bytes) {
            var temp = Integer.toHexString(b and 0xff)
            if (temp.length == 1) {
                temp = "0$temp"
            }
            result += temp
        }
        return result
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

/**
 *SharedPreferences
 */
fun getSp(name: String) = App.getApp().getSharedPreferences(name, Context.MODE_PRIVATE)


/**
 * SnackBar
 */
fun String.snackBar(view: View) =
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)

/**
 * 跳转页面
 */
fun <A> Activity.goA(clazz: Class<A>) = startActivity(Intent(this, clazz))

fun <A> Context.goA(clazz: Class<A>) = startActivity(Intent(this, clazz))

/**
 * 校验邮箱
 *
 * @param email
 * @return 校验通过返回true，否则返回false
 */
fun isEmail(email: String) = Pattern.matches(REGEX_EMAIL, email)

/**
 * 校验密码
 *
 * @param password
 * @return 校验通过返回true，否则返回false
 */
fun isPassword(password: String) = Pattern.matches(REGEX_PASSWORD, password)
