package com.example.user.mytem.adaper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.HomeDinamisFragment;

import java.util.List;

/**
 * Created by putuguna on 21/03/17.
 */

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    private Context ctx;
    private List<String> data;
    private Fragment[] fragments;

    public HomeFragmentAdapter( Context ctx, FragmentManager fm, List<String> data) {
        super(fm);
        this.ctx = ctx;
        this.data = data;
        fragments = new Fragment[data.size()];
    }

    @Override
    public Fragment getItem( int position) {
        Fragment fragment = null;
        String items = data.get(position);


        HomeDinamisFragment dinamisFragment = new HomeDinamisFragment();
        dinamisFragment.setDetail(items);
        fragment = dinamisFragment;

        if (fragments[position] == null) {
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }
}