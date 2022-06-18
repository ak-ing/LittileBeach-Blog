package com.example.littlebeachblog.ui.page.me.detailAc

import android.os.Bundle
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.goA
import com.example.littlebeachblog.app.utils.snackBar
import com.gyf.immersionbar.ImmersionBar

class AboutActivity : BaseActivity() {
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_aboudt)
            .addBindingParam(BR.click, AboutClickProxy())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "关于")

    }

    inner class AboutClickProxy {
        /**
         * 检查更新
         */
        fun checkUpdate() {
            "已是最新版本：1.0.0".snackBar(binding.root).show()
        }

        /**
         *  隐私协议
         */
        fun userProtocol() {
            goA(ProtocolActivity::class.java)
        }

        /**
         * 使用条例
         */
        fun userRegulation() {
            goA(RegulationActivity::class.java)
        }
    }

}