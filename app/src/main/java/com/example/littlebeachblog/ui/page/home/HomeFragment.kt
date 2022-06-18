package com.example.littlebeachblog.ui.page.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.goA
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.PageAdapter
import com.example.littlebeachblog.ui.page.home.home_page_child.MoYvFragment
import com.example.littlebeachblog.ui.page.home.home_page_child.SubscriptionFragment
import com.example.littlebeachblog.ui.page.login.LoginActivity
import com.example.littlebeachblog.ui.page.me.detailAc.PersonDetailActivity
import com.example.littlebeachblog.ui.state.HomeViewModel

class HomeFragment : BaseFragment() {

    private val mState: HomeViewModel by viewModels()
    private lateinit var mEvent: SharedViewModel
    private val fragments = listOf(SubscriptionFragment.newInstance(), MoYvFragment.newInstance(null))
    private val pageAdapter by lazy { PageAdapter(childFragmentManager, lifecycle).setFragmentSet(fragments) }

    override fun initViewModel() {
        setHasOptionsMenu(true)
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home, BR.vm, mState)
            .addBindingParam(BR.pageAdapter, pageAdapter)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.setSupportActionBar(binding.root.findViewById(R.id.toolbar_home))
        binding.root.post { mState.initTabAndPage.value = R.id.view_page_home }
        mEvent.userInfo.observe(viewLifecycleOwner) {
            mState.avatarUrl.value = it.avatar
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_menu_home) {
            if (mEvent.token.value.isNullOrEmpty()) {
                "用户未登录".snackBar(binding.root).setAction("去登陆") {
                    mActivity.goA(LoginActivity::class.java)
                }.show()
                return true
            }
            mActivity.goA(MoYvPublishActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        mState.initTabAndPage.value = 0
        super.onDestroyView()
    }

    inner class ClickProxy {

        fun offsetChanged(topView: View, height: Float, verticalOffset: Int) {
            topView.alpha = (height + verticalOffset) / height
        }

        fun avatarClick() {
            if (mEvent.userInfo.value == null) {
                mActivity.goA(LoginActivity::class.java)
            } else {
                mActivity.startActivity(
                    Intent(mActivity, PersonDetailActivity::class.java).putExtra(
                        "userId",
                        mEvent.userInfo.value?._id
                    )
                )
            }
        }

    }

}