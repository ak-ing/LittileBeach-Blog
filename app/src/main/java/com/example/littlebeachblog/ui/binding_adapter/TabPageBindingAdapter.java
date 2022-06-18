package com.example.littlebeachblog.ui.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Create by KunMinX at 2020/3/13
 */
public class TabPageBindingAdapter {

    @BindingAdapter(value = {"initTabAndPage2","currentPage"}, requireAll = false)
    public static void initTabAndPage2(TabLayout tabLayout, int pageId, int current) {
        if (pageId==0) {
            return;
        }
        int count = tabLayout.getTabCount();
        String[] title = new String[count];
        for (int i = 0; i < count; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null && tab.getText() != null) {
                title[i] = tab.getText().toString();
            }
        }
        ViewPager2 viewPager = (tabLayout.getRootView()).findViewById(pageId);
        if (viewPager != null) {
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tab.setText(title[position]);
            }).attach();
            viewPager.setCurrentItem(current);
        }
    }


    @BindingAdapter(value = {"tabSelectedListener"}, requireAll = false)
    public static void tabSelectedListener(TabLayout tabLayout, TabLayout.OnTabSelectedListener listener) {
        tabLayout.addOnTabSelectedListener(listener);
    }

}
