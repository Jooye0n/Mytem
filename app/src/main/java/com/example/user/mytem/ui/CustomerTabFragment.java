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
import com.example.user.mytem.adaper.CustomerTabPageAdapter;

public class CustomerTabFragment extends Fragment {

    private ViewPager mViewPager;

    public static int getCurrentTab() {
        return currentTab;
    }

    private static int currentTab;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentTab=0;
        View view = inflater.inflate(R.layout.activity_list_customer, container, false);
//        ((BoardActivity) getActivity()).setToolbarTitle("Board");

//        HorizontalScrollView scrollingView = view.findViewById(R.id.scroll_view);
//        scrollingView.setHorizontalScrollBarEnabled(false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.board_tab);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("B"));
        tabLayout.addTab(tabLayout.newTab().setText("C"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setVerticalScrollBarEnabled(false);

        mViewPager = (ViewPager) view.findViewById(R.id.board_view_pager);
        CustomerTabPageAdapter customerTabPageAdapter = new CustomerTabPageAdapter(getFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(customerTabPageAdapter);

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