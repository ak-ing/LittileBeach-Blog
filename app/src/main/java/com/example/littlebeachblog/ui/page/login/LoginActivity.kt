package com.example.littlebeachblog.ui.page.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.edit
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.MainActivity
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.*
import com.example.littlebeachblog.data.bean.LoginData
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.state.UserViewModel
import com.gyf.immersionbar.ImmersionBar
import java.util.*

class LoginActivity : BaseActivity() {

    private val mState by viewModels<UserViewModel>()
    private lateinit var mEvent: SharedViewModel

    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
            .fitsSystemWindows(true).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.vm, mState)
            .addBindingParam(BR.click, LoginClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSp("app")
        mState.emailLd.value = sp.getString("email", "")
        mState.pwdLd.value = sp.getString("password", "")

        mState.loginLd.observe(this) {

            if (it.loadState == UILoader.UIStatus.NETWORK_ERROR) {
                isShowLoad(false)
                return@observe "网络错误".snackBar(binding.root).show()
            }
            if (!it.isSuccess()) {
                isShowLoad(false)
                return@observe it.getMessage().snackBar(binding.root).show()
            }
            if (it.isSuccess() && it.getCode() == 200) {
                mEvent.token.value = it.getToken()
                App.getApp().mMap["token"] = it.getToken()
                sp.edit { putString("token", it.getToken()) }
                sp.edit { putString("email", mState.emailLd.value) }
                sp.edit { putString("password", mState.pwdLd.value) }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
    }

    inner class LoginClickProxy {
        fun userNameChanged(s: Editable) {
            if (s.length < 2) mState.emailLd.value = "$s"
            if (isEmail("$s")) mState.emailLd.value = "$s"
        }

        fun onAvatarLoad(state: Boolean) {
            mState.loadState.value = state
        }

        fun passWordChanged(s: Editable) {
            mState.pwdLd.value = "$s"
        }


        fun login() {
            hideSoftKeyboard()
            if (mState.emailLd.value.isNullOrEmpty())
                return "账户不能为空。".snackBar(binding.root).show()

            if (mState.pwdLd.value.isNullOrEmpty())
                return "密码不能为空。".snackBar(binding.root).show()
            isShowLoad(true)
            mState.login(
                LoginData(
                    mState.emailLd.value.toString().trim(),
                    mState.pwdLd.value!!.trim().md5().toString().trim().uppercase(Locale.getDefault())
                )
            )
        }

        fun findPassWord() {
            "暂不支持.o(╥﹏╥)o".snackBar(binding.root).show()
        }

        fun register() {
            goA(RegisterActivity::class.java)
        }

        fun skip() {
            goA(MainActivity::class.java)
            finish()
        }
    }

}