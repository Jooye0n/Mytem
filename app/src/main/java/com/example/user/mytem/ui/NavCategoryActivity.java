package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.adaper.ArrayListRecyclerAdapter;
import com.example.user.mytem.model.CartModel;
import com.example.user.mytem.model.OnDataChangedListener;
import com.example.user.mytem.model.PostModel;
import com.example.user.mytem.singleton.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NavCategoryActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private TextView toolbartext;
    private String CART_POSTTYPE;
    private ArrayList<Post> post;
    private ArrayListRecyclerAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private int categoryCount = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manager);
        recyclerView = (RecyclerView)findViewById(R.id.board_recycler_view);
        CART_POSTTYPE = getIntent().getExtras().getString("TITLE");
        post = new ArrayList <>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartext = findViewById(R.id.toolbartext);
        toolbartext.setText(CART_POSTTYPE);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);//역순으로 출력하기
        linearLayoutManager.setStackFromEnd(true);//스크롤 끝으로 보내기
        linearLayoutManager.scrollToPositionWithOffset(0, 0);//항목을 추가한 후 맨 위로 올라가기
        adapter = new ArrayListRecyclerAdapter(post);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query recentPostsQuery;

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_two)).orderByChild("category").equalTo(CART_POSTTYPE);
        Log.v("recentPostsQuery",recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_three)).orderByChild("category").equalTo(CART_POSTTYPE);
        Log.v("recentPostsQuery",recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_four)).orderByChild("category").equalTo(CART_POSTTYPE);
        Log.v("recentPostsQuery",recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_five)).orderByChild("category").equalTo(CART_POSTTYPE);
        Log.v("recentPostsQuery",recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_six)).orderByChild("category").equalTo(CART_POSTTYPE);
        Log.v("recentPostsQuery",recentPostsQuery.toString());
        findData(recentPostsQuery);
        //전체 탐색하여 category 에 해당하는 것을 가져옴

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void findData(Query recentPostsQuery) {
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                   post.add(snapshot.getValue(Post.class)); //카테고리에 해당하는 post가져오기
                    Log.v("카테고리",snapshot.getValue(Post.class).getContents());//데이터는 잘 찾음
                    Log.v("카테고리0",post.get(0).getContents());//post추가도 잘 됨
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                throw databaseError.toException();
            }
        });
    }

    private void showProgressDialog() { //진행중을 나타내는상태바

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

    public void setAdapter(Query query) {
        showProgressDialog();
        PostModel postModel = new PostModel();
        postModel.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
//                recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                새글 작성시 스크롤 최상단으로 이동
                progressDialog.dismiss();
            }
        });

        recyclerView.setAdapter(postModel.setAdapter(query, CART_POSTTYPE));
        progressDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}