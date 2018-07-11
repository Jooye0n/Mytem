package com.example.user.mytem.adaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.Board100000UnderFragment;
import com.example.user.mytem.ui.BoardHomeFragment;
import com.example.user.mytem.ui.Board100000UpperFragment;
import com.example.user.mytem.ui.Board30000UnderFragment;
import com.example.user.mytem.ui.BoardBestFragment;
import com.example.user.mytem.ui.Board50000UnderFragment;
import com.example.user.mytem.ui.Board10000UnderFragment;


public class BoardTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public BoardTabPageAdapter( FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem( int position) {

        switch (position) {
            case 0:
                return new BoardHomeFragment();

            case 1:
                return new BoardBestFragment();

            case 2:
                return new Board10000UnderFragment();

            case 3:
                return new Board30000UnderFragment();

            case 4:
                return new Board50000UnderFragment();

            case 5:
                return new Board100000UnderFragment();

            case 6:
                return new Board100000UpperFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
