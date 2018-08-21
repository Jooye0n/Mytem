package com.example.user.mytem.adaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.Board100000UnderFragment;
import com.example.user.mytem.ui.BoardDetail1Fragment;
import com.example.user.mytem.ui.BoardHomeFragment;
import com.example.user.mytem.ui.Board100000UpperFragment;
import com.example.user.mytem.ui.Board30000UnderFragment;
import com.example.user.mytem.ui.BoardBestFragment;
import com.example.user.mytem.ui.Board50000UnderFragment;
import com.example.user.mytem.ui.Board10000UnderFragment;
import com.example.user.mytem.ui.CommonTabFragment;


public class BoardTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private int type;

    public BoardTabPageAdapter( FragmentManager fm, int tabCount, int type) {
        super(fm);
        this.tabCount = tabCount;
        this.type = type;
    }

    @Override
    public Fragment getItem( int position) {

        switch (position) {
            case 0:
                return new BoardHomeFragment();

            case 1:
                return new BoardBestFragment();

            case 2:
                Board10000UnderFragment fragment2 = new Board10000UnderFragment();
                Bundle data2 = new Bundle();//Use bundle to pass data
                data2.putString("TYPE", String.valueOf(type));
                fragment2.setArguments(data2);
                return fragment2;

            case 3:
                Board30000UnderFragment fragment3 = new Board30000UnderFragment();
                Bundle data3 = new Bundle();//Use bundle to pass data
                data3.putString("TYPE", String.valueOf(type));
                fragment3.setArguments(data3);
                return fragment3;

            case 4:
                Board50000UnderFragment fragment4 = new Board50000UnderFragment();
                Bundle data4 = new Bundle();//Use bundle to pass data
                data4.putString("TYPE", String.valueOf(type));
                fragment4.setArguments(data4);
                return fragment4;

            case 5:
                Board100000UnderFragment fragment5 = new Board100000UnderFragment();
                Bundle data5 = new Bundle();//Use bundle to pass data
                data5.putString("TYPE", String.valueOf(type));
                fragment5.setArguments(data5);
                return fragment5;

            case 6:
                Board100000UpperFragment fragment6 = new Board100000UpperFragment();
                Bundle data6 = new Bundle();//Use bundle to pass data
                data6.putString("TYPE", String.valueOf(type));
                fragment6.setArguments(data6);
                return fragment6;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
