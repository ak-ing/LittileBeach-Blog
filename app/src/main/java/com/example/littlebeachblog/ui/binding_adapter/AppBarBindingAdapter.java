package com.example.littlebeachblog.ui.binding_adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.littlebeachblog.R;
import com.example.littlebeachblog.ui.page.home.HomeFragment;
import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by AK on 2022/1/31 23:26.
 * God bless my code!
 */
public class AppBarBindingAdapter {

    private static float topViewHeight = -1;

    /**
     * 首页AppbarLayout
     *
     * @param appBarLayout
     */
    @BindingAdapter(value = "onOffsetChangedListener", requireAll = false)
    public static void onOffsetChangedListener(AppBarLayout appBarLayout, HomeFragment.ClickProxy clickProxy) {
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            View topView = appBarLayout.findViewById(R.id.toolbar_home);
            if (topViewHeight == -1) {
                topViewHeight = topView.getHeight();
            }
            clickProxy.offsetChanged(topView, topViewHeight, verticalOffset);
        });
    }
}
