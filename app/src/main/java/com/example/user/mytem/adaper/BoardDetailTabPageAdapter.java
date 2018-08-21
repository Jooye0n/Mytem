package com.example.user.mytem.adaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mytem.ui.BoardDetail1Fragment;
import com.example.user.mytem.ui.BoardDetail2Fragment;
import com.example.user.mytem.ui.BoardDetail3Fragment;

public class BoardDetailTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private String contents = null;
    private String detail = null;

    public BoardDetailTabPageAdapter( FragmentManager fm, int tabCount, String contents, String detail) {
        super(fm);
        this.tabCount = tabCount;
        this.contents = contents;
        this.detail = detail;
    }

    public BoardDetailTabPageAdapter( FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    //        Fragment fragment = new BoardDetailTabFragment();
    //        Bundle data = new Bundle();//Use bundle to pass data
    //        data.putString("Contents", contents.getText().toString());
    //        data.putString("Detail",getIntent().getExtras().getString("POST_DETAIL"));
    //        fragment.setArguments(data);

    @Override
    public Fragment getItem( int position) {

        switch (position) {
            case 0:
                BoardDetail1Fragment fragment = new BoardDetail1Fragment();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("Contents", contents);
                data.putString("Detail",detail);
                fragment.setArguments(data);
                return fragment;

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