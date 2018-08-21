package com.example.user.mytem.ui;

import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.adaper.ArrayListRecyclerAdapter;
import com.example.user.mytem.model.OnDataChangedListener;
import com.example.user.mytem.model.PostModel;
import com.example.user.mytem.singleton.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NavSearchActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ArrayList<Post> post;
    private ArrayList<Post> list;
    private ArrayListRecyclerAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);
        recyclerView = (RecyclerView) findViewById(R.id.board_recycler_view);
        post = new ArrayList <>();
        list = new ArrayList <>();

        editText = findViewById(R.id.editText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);//역순으로 출력하기
        linearLayoutManager.setStackFromEnd(true);//스크롤 끝으로 보내기
        linearLayoutManager.scrollToPositionWithOffset(0, 0);//항목을 추가한 후 맨 위로 올라가기


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query recentPostsQuery;

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_two));
        Log.v("recentPostsQuery", recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_three));
        Log.v("recentPostsQuery", recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_four));
        Log.v("recentPostsQuery", recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_five));
        Log.v("recentPostsQuery", recentPostsQuery.toString());
        findData(recentPostsQuery);

        recentPostsQuery = databaseReference.child(getResources().getString(R.string.tab_six));
        Log.v("recentPostsQuery", recentPostsQuery.toString());
        findData(recentPostsQuery);
        //전체 탐색하여 category 에 해당하는 것을 가져옴

//        list.addAll(post);
        adapter = new ArrayListRecyclerAdapter(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editText.getText().toString();
                search(text);
            }
        });
    }

    public void findData(Query recentPostsQuery) {
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    post.add(snapshot.getValue(Post.class)); //카테고리에 해당하는 post가져오기
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                throw databaseError.toException();
            }
        });
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.clear();
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < post.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (post.get(i).getContents().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(post.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
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

        recyclerView.setAdapter(postModel.setAdapter(query, getResources().getString(R.string.tab_two)));
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