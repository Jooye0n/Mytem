package com.example.user.mytem.adaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.CustomerAFragment;
import com.example.user.mytem.ui.CustomerBFragment;
import com.example.user.mytem.ui.CustomerCFragment;

public class CustomerTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public CustomerTabPageAdapter( FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem( int position) {

        switch (position) {
            case 0:
                return new CustomerAFragment();

            case 1:
                return new CustomerBFragment();

            case 2:
                return new CustomerCFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
