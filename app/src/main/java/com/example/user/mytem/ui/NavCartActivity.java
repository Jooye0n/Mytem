package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.model.CartModel;
import com.example.user.mytem.model.OnDataChangedListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NavCartActivity extends AppCompatActivity {

    final String CART_POSTTYPE = "장바구니";
    private ActionBar actionBar;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private TextView toolbartext;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manager);

        recyclerView = (RecyclerView)findViewById(R.id.board_recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartext = findViewById(R.id.toolbartext);
        toolbartext.setText("장바구니");

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        setAdapter(databaseReference.child("장바구니"));


    }

    public void setAdapter(Query query) {
        showProgressDialog();
        CartModel cartModel = new CartModel();
        cartModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
//                recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                새글 작성시 스크롤 최상단으로 이동

                progressDialog.dismiss();
            }
        });

        recyclerView.setAdapter(cartModel.setAdapter(query, CART_POSTTYPE));
        progressDialog.dismiss();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                this.finish();
                return true;
            }
        }
        return false;
    }

}