package com.example.littlebeachblog.ui.page.me.child_page

import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.FooterAdapter
import com.example.littlebeachblog.ui.adapter.MoYvListAdapter
import com.example.littlebeachblog.ui.state.MeViewModel

/**
 * Created by AK on 2022/3/2 14:37.
 * God bless my code!
 */
class PersonFishFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = PersonFishFragment()
    }

    val mState by viewModels<MeViewModel>()
    private lateinit var mEvent: SharedViewModel
    private val moYvAdapter = MoYvListAdapter()

    override fun initViewModel() {
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun hasLoader(): Boolean {
        return true
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_person_fish, BR.vm, mState)
            .addBindingParam(
                BR.adapter, moYvAdapter
                    .withLoadStateFooter(FooterAdapter { moYvAdapter.retry() })
            )
            .addBindingParam(BR.click, PersonFishClickProxy())
    }

    inner class PersonFishClickProxy {
        /**
         * 下拉刷新
         */
        fun refresh() = moYvAdapter.refresh()
    }
}