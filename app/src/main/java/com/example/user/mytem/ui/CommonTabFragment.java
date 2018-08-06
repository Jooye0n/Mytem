package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mytem.R;
import com.example.user.mytem.model.OnDataChangedListener;
import com.example.user.mytem.model.PostModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public abstract class CommonTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_board, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.board_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        setHasOptionsMenu(true);

        Log.i("실행되는지보려고","1");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);

        setAdapter(getRef());
        return view;
    }


    public abstract DatabaseReference getRef();

    public abstract String getPostType();




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
        Log.i("실행되는지보려고","2");

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
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

    @Override
    public void onRefresh() {

        Log.i("실행되는지보려고","3");

        PostModel postModel = new PostModel(getPostType());
        postModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {//바뀔때마다 불린다.
//                recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                새글 작성시 스크롤 최상단으로 이동
                progressDialog.dismiss();
            }
        });

        recyclerView.setAdapter(postModel.setAdapter(getRef(), getPostType()));

        //새로고침 완료시
        swipeRefreshLayout.setRefreshing(false);
    }

}