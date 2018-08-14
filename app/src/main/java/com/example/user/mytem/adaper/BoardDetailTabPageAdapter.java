package com.example.user.mytem.adaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.BoardDetail1Fragment;
import com.example.user.mytem.ui.BoardDetail2Fragment;
import com.example.user.mytem.ui.BoardDetail3Fragment;

public class BoardDetailTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public BoardDetailTabPageAdapter( FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem( int position) {

        switch (position) {
            case 0:
                return new BoardDetail1Fragment();

            case 1:
                return new BoardDetail2Fragment();

            case 2:
                return new BoardDetail3Fragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}