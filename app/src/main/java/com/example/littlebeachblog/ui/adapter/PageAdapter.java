package com.example.littlebeachblog.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AK on 2022/1/27 14:11.
 * God bless my code!
 */
public class PageAdapter extends FragmentStateAdapter {
    /**
     * Fragment 集合
     */
    private final List<Fragment> mFragmentSet = new ArrayList<>();

    public PageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    /**
     * 添加 Fragment
     */
    public void addFragment(Fragment fragment) {
        mFragmentSet.add(fragment);
        notifyDataSetChanged();
    }

    public PageAdapter setFragmentSet(List<Fragment> fragmentSet) {
        mFragmentSet.clear();
        mFragmentSet.addAll(fragmentSet);
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentSet.size();
    }
}
