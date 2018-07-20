package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mytem.R;
import com.example.user.mytem.adaper.BoardTabPageAdapter;


public class BoardTabFragment extends Fragment {

    private ViewPager mViewPager;

    public static int getCurrentTab() {
        return currentTab;
    }

    private static int currentTab;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentTab=0;
        View view = inflater.inflate(R.layout.fragment_board, container, false);
//        ((BoardActivity) getActivity()).setToolbarTitle("Board");

//        HorizontalScrollView scrollingView = view.findViewById(R.id.scroll_view);
//        scrollingView.setHorizontalScrollBarEnabled(false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.board_tab);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("BEST"));
        tabLayout.addTab(tabLayout.newTab().setText("만원미만"));
        tabLayout.addTab(tabLayout.newTab().setText("3만원미만"));
        tabLayout.addTab(tabLayout.newTab().setText("5만원미만"));
        tabLayout.addTab(tabLayout.newTab().setText("10만원미만"));
        tabLayout.addTab(tabLayout.newTab().setText("10만원이상"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setVerticalScrollBarEnabled(false);

        mViewPager = (ViewPager) view.findViewById(R.id.board_view_pager);
        BoardTabPageAdapter boardTabPageAdapter = new BoardTabPageAdapter(getFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(boardTabPageAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                currentTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
