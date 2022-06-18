package com.example.littlebeachblog.ui.page.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.littlebeachblog.MainActivity
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.app.utils.getSp
import com.example.littlebeachblog.app.utils.goA
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.page.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val mEvent = ViewModelProvider(App.getApp()).get<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val str = getSp("app").getString("token", "")
        mEvent.token.value = str
        if (str.isNullOrEmpty()) {
            goA(LoginActivity::class.java)
            finish()
        } else {
            goA(MainActivity::class.java)
            finish()
        }
    }
}