package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.adaper.BoardDetailTabPageAdapter;
import com.example.user.mytem.adaper.BoardTabPageAdapter;

public class BoardDetailTabFragment extends Fragment {

    private ViewPager mViewPager;
    private ImageView detailImage;
    private TextView detailTextView;
    private String detailString;
    private String contentsString;
    public static int getCurrentTab() {
        return currentTab;
    }

    private static int currentTab;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentTab=0;
        final View view = inflater.inflate(R.layout.common_tab_and_viewpager, container, false);
//        ((BoardActivity) getActivity()).setToolbarTitle("Board");

//        HorizontalScrollView scrollingView = view.findViewById(R.id.scroll_view);
//        scrollingView.setHorizontalScrollBarEnabled(false);

            detailString = getArguments().getString("Detail");
            contentsString = getArguments().getString("Contents");
            Log.v("DetailDetailDetailDetail", detailString);
            Log.v("DetailDetailDetailDetail", contentsString);//정상출력된다

        //        Fragment fragment = new BoardDetailTabFragment();
        //        Bundle data = new Bundle();//Use bundle to pass data
        //        data.putString("Contents", contents.getText().toString());
        //        data.putString("Detail",getIntent().getExtras().getString("POST_DETAIL"));
        //        fragment.setArguments(data);


        ImageView filterbtn = view.findViewById(R.id.filter);
        filterbtn.setVisibility(View.GONE);


        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.board_tab);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("상세정보"));
        tabLayout.addTab(tabLayout.newTab().setText("사용후기"));
        tabLayout.addTab(tabLayout.newTab().setText("Q&A"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) view.findViewById(R.id.board_view_pager);
        BoardDetailTabPageAdapter boardTabPageAdapter = new BoardDetailTabPageAdapter(getFragmentManager(), tabLayout.getTabCount(),contentsString, detailString);
        mViewPager.setAdapter(boardTabPageAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                currentTab = tab.getPosition();
                if(currentTab == 0) {
                    BoardDetailTabPageAdapter boardTabPageAdapter = new BoardDetailTabPageAdapter(getFragmentManager(), tabLayout.getTabCount(),contentsString, detailString);
                    mViewPager.setAdapter(boardTabPageAdapter);
                } else {
                    BoardDetailTabPageAdapter boardTabPageAdapter = new BoardDetailTabPageAdapter(getFragmentManager(), tabLayout.getTabCount());
                    mViewPager.setAdapter(boardTabPageAdapter);
                }
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

