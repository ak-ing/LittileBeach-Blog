package com.example.littlebeachblog.ui.page.me.detailAc

import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.MainActivity
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.*
import com.example.littlebeachblog.databinding.ActivityPasswordBinding
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.ui.state.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class PasswordActivity : BaseActivity() {
    val mState by viewModels<UserViewModel>()
    private lateinit var mEvent: SharedViewModel
    private lateinit var bind: ActivityPasswordBinding
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_password, BR.vm, mState)
            .addBindingParam(BR.click, PasswordClickProxy())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "修改密码")
        bind = binding as ActivityPasswordBinding
    }

    /**
     * 检查以确定按钮是否可用
     */
    fun checkIsEnable() {
        mState.run {
            btnEnable.value =
                !(emailLd.value.isNullOrEmpty() || pwdLd.value.isNullOrEmpty() || pwdAgainLd.value.isNullOrEmpty() || verifyCodeLd.value.isNullOrEmpty())
        }
    }

    inner class PasswordClickProxy {

        fun userNameChanged(s: Editable) {
            mState.emailLd.value = "$s"
            checkIsEnable()
        }

        fun passWordChanged(s: Editable) {
            mState.pwdLd.value = "$s"
            bind.textInputLayout5.isErrorEnabled = false
            checkIsEnable()
        }

        fun passWordAgain(s: Editable) {
            mState.pwdAgainLd.value = "$s"
            bind.textInputLayout5.isErrorEnabled = false
            checkIsEnable()
        }

        fun verifyCode(s: Editable) {
            mState.verifyCodeLd.value = "$s"
            checkIsEnable()
        }

        /**
         * 发送验证码
         */
        fun requestVerifyCode() {
            mState.verifyEnable.value = false
            lifecycleScope.launch {
                mEvent.userInfo.value?.let {
                    var code = ""
                    (1..6).map {
                        code += (0..9).random()
                    }
                    mState.verifyCode.value = code
                    val body = mapOf("toEmail" to it.userName, "code" to code)
                    if (mState.requestSendEmail(body).isSuccess()) {
                        "验证码已发送到您的邮箱,请注意查收.".snackBar(binding.root).show()
                    } else {
                        "发送失败,未知错误.".snackBar(binding.root).show()
                    }
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
         * 修改密码
         */
        fun callChange() {
            if (mState.pwdLd.value != mState.pwdAgainLd.value) {
                bind.textInputLayout5.error = "两次输入不一致"
                bind.textInputLayout5.isErrorEnabled = true
                return
            }
            if (mState.verifyCodeLd.value != mState.verifyCode.value) {
                return "验证码错误.".snackBar(bind.rootView).show()
            }
            hideSoftKeyboard()
            isShowLoad(true)
            val old = mState.emailLd.value!!.trim().md5().toString().trim().uppercase(Locale.getDefault())
            val new = mState.pwdAgainLd.value!!.trim().md5().toString().trim().uppercase(Locale.getDefault())
            val body = mapOf("passWord" to new, "oldPassWord" to old)
            lifecycleScope.launch {
                val result = mState.requestUpdatePwd(body)
                isShowLoad(false)
                if (result.isSuccess()) {
                    val sp = getSp("app")
                    sp.edit { putString("password", mState.pwdLd.value) }
                    sp.edit { remove("token") }
                    App.getApp().viewModelStore.clear()
                    "修改成功,请重新登录.".showLong()
                    goA(LoginActivity::class.java)
                    ActivityController.finishOne(MainActivity::class.java)
                    finish()
                } else result.getMessage().snackBar(binding.root).show()
            }
        }
    }

}