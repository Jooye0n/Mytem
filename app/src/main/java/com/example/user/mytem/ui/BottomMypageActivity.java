package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BottomMypageActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private TextView mypageId;
    private TextView mypagePoint;
    private TextView mypageLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        mypageId = findViewById(R.id.mypage_id);
        mypageLevel = findViewById(R.id.mypage_lv);
        mypagePoint = findViewById(R.id.mypage_p);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {///로그인 된 상태라면 //확인절차
            mypageId.setText(user.getEmail());
            mypagePoint.setText("23P");

            /*
            이메일로 realtime DB찾기
             */
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            String s = user.getEmail();
            Query recentPostsQuery;

            recentPostsQuery = databaseReference.child("AUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery",recentPostsQuery.toString());
            findData(recentPostsQuery);

            recentPostsQuery = databaseReference.child("BUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery",recentPostsQuery.toString());
            findData(recentPostsQuery);

            recentPostsQuery = databaseReference.child("CUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery",recentPostsQuery.toString());
            findData(recentPostsQuery);
            //전부찾기
        }

    }

    public void findData(Query recentPostsQuery) {
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    mypageLevel.setText(snapshot.child("userType").getValue(String.class));
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                throw databaseError.toException();
            }
        });
    }

    //toolbar의 back키 눌렀을 때 동작
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()){
            case android.R.id.home:{
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

