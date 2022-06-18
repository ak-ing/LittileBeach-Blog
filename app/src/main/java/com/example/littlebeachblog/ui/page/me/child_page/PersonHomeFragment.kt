package com.example.littlebeachblog.ui.page.me.child_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseFragment
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.goA
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.page.me.detailAc.PersonEditActivity
import com.example.littlebeachblog.ui.state.MeViewModel

/**
 * Created by AK on 2022/3/2 14:34.
 * God bless my code!
 */
class PersonHomeFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = PersonHomeFragment()
    }

    val mState by viewModels<MeViewModel>()
    private lateinit var mEvent: SharedViewModel
    override fun initViewModel() {
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_person_home, BR.vm, mState)
            .addBindingParam(BR.click, PersonHomeClickProxy())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEvent.userInfo.observe(this) {
            if (it != null) {
                mState.userInfo.value = it
            }
        }
    }

    inner class PersonHomeClickProxy {
        fun edit() {
            if (mEvent.userInfo.value == null) {
                "用户未登录.".snackBar(binding.root).show()
            } else {
                mActivity.goA(PersonEditActivity::class.java)
            }
        }
    }
}