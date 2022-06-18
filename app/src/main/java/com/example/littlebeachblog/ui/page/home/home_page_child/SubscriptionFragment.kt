package com.example.littlebeachblog.ui.page.home.home_page_child

import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.ui.state.home_state_child.SubscriptionViewModel

/**
 * Created by AK on 2022/1/31 18:17.
 * God bless my code!
 */
class SubscriptionFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SubscriptionFragment()
    }

    override fun initViewModel() {

    }

    private val mState: SubscriptionViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_subscription, BR.vm, mState)
    }
}