package com.example.user.mytem.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.user.mytem.R;
import com.example.user.mytem.adaper.HomeFragmentAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class BoardHomeFragment extends CommonTabFragment implements ViewPager.OnPageChangeListener{

    private RecyclerView recyclerView;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private AutoScrollViewPager viewPager;
    private List<String> listItem = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;

    public BoardHomeFragment() {}

    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.board_recycler_view);
        RelativeLayout relativeLayout = view.findViewById(R.id.item_main);
        setHasOptionsMenu(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        recyclerView.setLayoutManager(linearLayoutManager);

        indicator = (LinearLayout) view.findViewById(R.id.indicators);
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.viewPager_itemList);

        viewPager.setInterval(3000); // 페이지 넘어갈 시간 간격 설정
        viewPager.startAutoScroll(); //Auto Scroll 시작
        setData(getContext());

        return view;
    }

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("HOME");
    }

    @Override
    public String getPostType() {
        return "HOME";
    }

    private void setData( Context context){
        listItem.add("Ini adalah fragment 1");
        listItem.add("Ini adalah fragment 2");
        listItem.add("Ini adalah fragment 3");
        listItem.add("Ini adalah fragment 4");
        listItem.add("Ini adalah fragment 5");

        homeFragmentAdapter = new HomeFragmentAdapter(context, getActivity().getSupportFragmentManager(), listItem);
        viewPager.setAdapter(homeFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
        setUiPageViewController();

    }

    private void setUiPageViewController(){
        mDotCount = homeFragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for(int i=0; i<mDotCount; i++){
            mDots[i] = new LinearLayout(this.getActivity());
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4,0,4,0);
            indicator.addView(mDots[i],params);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i=0; i<mDotCount; i++){
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[position].setBackgroundResource(R.drawable.selected_item);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

