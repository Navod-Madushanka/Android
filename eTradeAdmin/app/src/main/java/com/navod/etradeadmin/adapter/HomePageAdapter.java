package com.navod.etradeadmin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.navod.etradeadmin.DeliveryFragment;
import com.navod.etradeadmin.HomeFragment;
import com.navod.etradeadmin.OrderFragment;
import com.navod.etradeadmin.SettingsFragment;
import com.navod.etradeadmin.UserFragment;

public class HomePageAdapter extends FragmentPagerAdapter {
    private final int pageCount = 5;
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
                return new OrderFragment();
            case (2):
                return new UserFragment();
            case (3):
                return new DeliveryFragment();
            case (4):
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
