package com.navod.etrade.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.navod.etrade.UpdateUserFragment1;
import com.navod.etrade.UpdateUserfragment2;

public class UpdateUserAdapter extends FragmentPagerAdapter {
    private static final int pageCount = 2;
    public UpdateUserAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new UpdateUserFragment1();
            case 1:
                return new UpdateUserfragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
