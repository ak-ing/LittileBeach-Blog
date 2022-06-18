package com.example.littlebeachblog.ui.page.login

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.isEmail
import com.example.littlebeachblog.app.utils.md5
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.databinding.DialogTagSelectBinding
import com.example.littlebeachblog.ui.adapter.AvatarRecommendAdapter
import com.example.littlebeachblog.ui.adapter.FishTagAdapter
import com.example.littlebeachblog.ui.binding_adapter.RecyclerViewBindingAdapter
import com.example.littlebeachblog.ui.state.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.util.*

class RegisterActivity : BaseActivity() {
    val mState by viewModels<UserViewModel>()
    private val dialog by lazy { BottomSheetDialog(this) }

    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).init()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_register, BR.vm, mState)
            .addBindingParam(BR.click, RegisterClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //头像
        val dlView = DialogTagSelectBinding.inflate(layoutInflater)
        val avatarAdapter = AvatarRecommendAdapter()
        RecyclerViewBindingAdapter.addItemDecoration(dlView.rvFishTag, 3)
        dlView.rvFishTag.adapter = avatarAdapter
        dlView.rvFishTag.layoutManager = GridLayoutManager(this, 5)
        dialog.setContentView(dlView.root)
        avatarAdapter.setOnItemClickListener { _, avatar, i ->
            dialog.dismiss()
            mState.avatarLd.value = avatar.picUrl
        }

        mState.avatarRecommend.observe(this) {
            if (it.isSuccess()) {
                mState.avatarLd.value = it.getData()[0].picUrl
                avatarAdapter.submitList(it.getData())
            }
        }

        mState.requestAvatarRecommend()

    }

    /**
     * 检查以确定按钮是否可用
     */
    fun checkIsEnable() {
        mState.run {
            btnEnable.value =
                !(nickLd.value.isNullOrEmpty() || emailLd.value.isNullOrEmpty() || pwdLd.value.isNullOrEmpty() || verifyCodeLd.value.isNullOrEmpty())
        }
    }

    inner class RegisterClickProxy {
        fun back() {
            finish()
        }

        fun selectAvatar() {
            dialog.show()
        }

        fun afterNickName(s: Editable) {
            mState.nickLd.value = "$s"
            checkIsEnable()
        }

        fun afterEmail(s: Editable) {
            mState.emailLd.value = "$s"
            checkIsEnable()
        }

        fun afterPassword(s: Editable) {
            mState.pwdLd.value = "$s"
            checkIsEnable()
        }


        fun afterVerifyCode(s: Editable) {
            mState.verifyCodeLd.value = "$s"
            checkIsEnable()
        }

        /**
         * 发送验证码
         */
        fun requestVerifyCode() {
            if (mState.emailLd.value.isNullOrEmpty() || !isEmail(mState.emailLd.value!!)) {
                "请输入有效邮箱号.".snackBar(binding.root).show()
                return
            }
            mState.verifyEnable.value = false
            lifecycleScope.launch {
                var code = ""
                (1..6).map {
                    code += (0..9).random()
                }
                mState.verifyCode.value = code
                val body = mapOf("toEmail" to mState.emailLd.value!!, "code" to code)
                if (mState.requestSendEmail(body).isSuccess()) {
                    "验证码已发送到您的邮箱,请注意查收.".snackBar(binding.root).show()
                } else {
                    "发送失败,未知错误.".snackBar(binding.root).show()
                }
            }

            lifecycleScope.launch(Dispatchers.IO) {
                var time = 60
                while (true) {
                    mState.verifyText.postValue("$time S")
                    delay(1000)
                    if (--time == 0) {
                        mState.verifyEnable.postValue(true)
                        mState.verifyText.postValue("获取验证码")
                        break
                    }
                }
            }
        }

        /**
         * 注册账户
         */
        fun register() {
            if (mState.verifyCodeLd.value != mState.verifyCode.value) {
                return "验证码错误.".snackBar(binding.root).show()
            }
            hideSoftKeyboard()
            isShowLoad(true)
            val email = mState.emailLd.value
            val pwd = mState.pwdLd.value!!.trim().md5().toString().trim().uppercase(Locale.getDefault())
            val nick = mState.nickLd.value
            val avatar = mState.avatarLd.value
            val body =
                mapOf("userName" to "$email", "passWord" to pwd, "nickName" to "$nick", "avatar" to "$avatar")
            lifecycleScope.launch {
                val result = mState.requestRegister(body)
                isShowLoad(false)
                if (result.isSuccess()) {
                    "注册成功.".snackBar(binding.root).addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (!isFinishing) {
                                finish()
                            }
                        }
                    }).show()
                } else {
                    "注册失败:${result.getMessage()}".snackBar(binding.root).show()
                }
            }
        }
    }
}