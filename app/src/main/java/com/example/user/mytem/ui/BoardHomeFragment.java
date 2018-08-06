package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v4.app.Fragment;
import com.example.user.mytem.R;
import com.example.user.mytem.adaper.HomeFragmentAdapter;
import com.example.user.mytem.model.OnDataChangedListener;
import com.example.user.mytem.model.PostModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class BoardHomeFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private RecyclerView recyclerView;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private AutoScrollViewPager viewPager;
    private List<String> listItem = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;
    private ProgressDialog progressDialog;
    private boolean onstart = false;

    public BoardHomeFragment() {}

    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        Log.i("onStart", "onCreateView첫 실행");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.board_recycler_view_home);
        setHasOptionsMenu(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        recyclerView.setLayoutManager(linearLayoutManager);

        setAdapter(getRef());

        indicator = (LinearLayout) view.findViewById(R.id.indicators);
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.viewPager_itemList);

        viewPager.setInterval(3000); // 페이지 넘어갈 시간 간격 설정
        viewPager.startAutoScroll(); //Auto Scroll 시작
        setData(getContext());

        onstart = true;
        Log.i("onStart", "onCreateView나중 실행");
        return view;
    }

    @Override
    public void onStart() {
        Log.i("onStart", "onstart실행");
        super.onStart();
       if(onstart == true) {
           homeFragmentAdapter = new HomeFragmentAdapter(getContext(), getActivity().getSupportFragmentManager(), listItem);
           viewPager.setAdapter(homeFragmentAdapter);
           viewPager.setCurrentItem(0);
           for (int i=1; i<mDotCount; i++) {
               mDots[i].setBackgroundResource(R.drawable.nonselected_item);
           }
           mDots[0].setBackgroundResource(R.drawable.selected_item);
           onstart = false;
       }


    }
    @Override
    public void onCreateOptionsMenu( final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.board_tool_menu, menu);


        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("제목으로 검색");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//검색 기능
            @Override
            public boolean onQueryTextSubmit(String s) {
                Query query = getRef().orderByChild("title").startAt(s).endAt(s + "\uf8ff");//입력한 문자열로 시작하는 title의 child만 list로 나열
                setAdapter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0)
                    setAdapter(getRef());
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        setAdapter(getRef());
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {//확장되었을때
                        return true;
                    }
                });
    }

    public void setAdapter(Query query) {
        showProgressDialog();
        PostModel postModel = new PostModel(getPostType());
        postModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {//바뀔때마다 불린다.
//                recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                새글 작성시 스크롤 최상단으로 이동
                progressDialog.dismiss();
            }
        });

        recyclerView.setAdapter(postModel.setAdapter(query, getPostType()));
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }


    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("HOME");
    }

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

