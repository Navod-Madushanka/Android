package com.navod.etradeadmin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.navod.etradeadmin.AddProduct1Fragment;
import com.navod.etradeadmin.AddProduct2Fragment;
import com.navod.etradeadmin.AddProduct3Fragment;

public class PageFormAdapter extends FragmentPagerAdapter {

    private final int pageCount = 3;

    public PageFormAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case (0):
                return new AddProduct1Fragment();
            case (1):
                return new AddProduct2Fragment();
            case (2):
                return new AddProduct3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
