package com.navod.etrade.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.navod.etrade.HomeFragment;
import com.navod.etrade.NotificationFragment;
import com.navod.etrade.SearchFragment;
import com.navod.etrade.UserFragment;

public class HomePageAdapter extends FragmentPagerAdapter {
    private final int pageCount = 4;
    public HomePageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case (0):
                return new HomeFragment();
            case (1):
                return new SearchFragment();
            case (2):
                return new UserFragment();
            case (3):
                return new NotificationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
