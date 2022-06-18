package com.example.littlebeachblog.ui.page.me.detailAc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.gyf.immersionbar.ImmersionBar

class ProtocolActivity : BaseActivity() {
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_protocol)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "隐私协议")

    }

}