package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.user.mytem.R;
import com.example.user.mytem.model.CUserModel;
import com.example.user.mytem.model.OnDataChangedListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public abstract class CustomerCommonTabFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;
    private Boolean check = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_customer, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.customer_recycler_view);
        setHasOptionsMenu(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        recyclerView.setLayoutManager(linearLayoutManager);

        checkBox = view.findViewById(R.id.checkBox);
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
                Query query = getRef().orderByChild("userName").startAt(s).endAt(s + "\uf8ff");
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
        CUserModel userModel = new CUserModel(getPostType());
        userModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                progressDialog.dismiss();
            }
        });
        recyclerView.setAdapter(userModel.setAdapterNoneCheck(query, getPostType()));
    }

    @Override
    public void onPrepareOptionsMenu( Menu menu) {///////////////쓰기 안보이게 (사용자는 작성할 수 없다.)도 control가능
        super.onPrepareOptionsMenu(menu);

        //아이콘 바꿔서 추가 말고 고객 등급 변경으로 수정
        MenuItem item3  = menu.findItem(R.id.menu_write);
        item3.setIcon(R.drawable.diamond);

    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item ) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                getActivity().finish();
                return true;
            }
            case R.id.menu_write: { //고객등급변경 버튼 누른경우 -> 코드 줄일 수 있을것 같다
                if(check == false) {//check박스가 생긴 상태로 변경되었음
                    item.setIcon(R.drawable.close);

                    CUserModel userModel = new CUserModel(getPostType());
                    userModel.setOnDataChangedListener(new OnDataChangedListener() {
                        @Override
                        public void onDataChanged() {
                            progressDialog.dismiss();
                        }
                    });
                    recyclerView.setAdapter(userModel.setAdapterCheck(getRef(), getPostType()));
                    check = true;
                }else { //check박스가 다시 사라진다.
                    item.setIcon(R.drawable.diamond);
                    setAdapter(getRef());
                    check = false;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }
}