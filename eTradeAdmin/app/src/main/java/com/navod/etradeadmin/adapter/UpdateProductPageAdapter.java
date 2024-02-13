package com.navod.etradeadmin.adapter;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.navod.etradeadmin.UpdateProductPage1Fragment;
import com.navod.etradeadmin.UpdateProductPage2Fragment;

public class UpdateProductPageAdapter extends FragmentPagerAdapter {
    private final int pageCount = 2;

    public UpdateProductPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case (0):
                return new UpdateProductPage1Fragment();
            case(1):
                return new UpdateProductPage2Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
