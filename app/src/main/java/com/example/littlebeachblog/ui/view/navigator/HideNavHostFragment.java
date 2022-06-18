package com.example.littlebeachblog.ui.view.navigator;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.example.littlebeachblog.R;


public class HideNavHostFragment extends NavHostFragment {

    @Deprecated
    @NonNull
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new HideSwitchFragmentNavigator(requireContext(), getChildFragmentManager(),
                getContainerId());
    }

    private int getContainerId() {
        int id = getId();
        if (id != 0 && id != View.NO_ID) {
            return id;
        }
        // Fallback to using our own ID if this Fragment wasn't added via
        // add(containerViewId, Fragment)
        return R.id.nav_host_fragment_container;
    }

}
