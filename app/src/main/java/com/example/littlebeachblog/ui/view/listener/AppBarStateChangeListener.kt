package com.example.littlebeachblog.ui.view.listener

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Created by AK on 2022/2/23 18:09.
 * God bless my code!
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED, SNAP, IDLE, COLLAPSED
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        when {
            offset == 0 -> {
                if (mCurrentState != State.EXPANDED) mCurrentState = State.EXPANDED
            }

            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) mCurrentState = State.COLLAPSED
            }

            abs(offset) <= appBarLayout.totalScrollRange * 0.75 -> {        //SNAP -> 可见率大于25%
                if (mCurrentState != State.SNAP) mCurrentState = State.SNAP
            }

            else -> {
                if (mCurrentState != State.IDLE) mCurrentState = State.IDLE
            }
        }
        onStateChanged(appBarLayout, offset, mCurrentState);
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, offset: Int, state: State)
}