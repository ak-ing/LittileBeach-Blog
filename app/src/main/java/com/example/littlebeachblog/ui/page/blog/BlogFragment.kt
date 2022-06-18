package com.example.littlebeachblog.ui.page.blog

import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.ui.state.BlogViewModel


class BlogFragment : BaseFragment() {
    private val mState: BlogViewModel by viewModels()

    override fun initViewModel() {

    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_blog, BR.vm, mState)
            .addBindingParam(BR.click, ClickProxy())
    }

    class ClickProxy {

    }
}