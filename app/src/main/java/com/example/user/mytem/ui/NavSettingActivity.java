package com.example.user.mytem.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NavSettingActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private LinearLayout manager;
    private LinearLayout user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        manager = findViewById(R.id.manager_linearlayout);
        user = findViewById(R.id.user_linearLayout);

        manager.setVisibility(View.INVISIBLE);
        user.setVisibility(View.INVISIBLE);

        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if(user1!=null) {//로그인 되어있으면
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            String s = user1.getEmail();
            Query recentPostsQuery;

            recentPostsQuery = databaseReference.child("AUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery1", recentPostsQuery.toString());
            findData(recentPostsQuery);

            recentPostsQuery = databaseReference.child("BUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery2", recentPostsQuery.toString());
            findData(recentPostsQuery);

            recentPostsQuery = databaseReference.child("CUser").orderByChild("uemail").equalTo(s);
            Log.v("recentPostsQuery3", recentPostsQuery.toString());
            findData(recentPostsQuery);

            recentPostsQuery = databaseReference.child("SUser").orderByChild("memail").equalTo(s);
            Log.v("recentPostsQuery4", recentPostsQuery.toString());
            findData2(recentPostsQuery);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);


        Button deleteButton = findViewById(R.id.delete_btn);//회원 정보 삭제
        Button versionButton = findViewById(R.id.version_button);//버전정보
        Button queryButton = findViewById(R.id.query_button);//도움말
        Button mancustButton = findViewById(R.id.manager_custlist);//고객목록
        Button manmanagerButton = findViewById(R.id.manager_manlist);//관리자목록
        final Button logoutButton = findViewById(R.id.logout_btn);//로그아웃버튼
        Button writePostButton = findViewById(R.id.post_write);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {//////////////////////////////////////////회원정보 삭제

                AlertDialog.Builder alt_bld = new AlertDialog.Builder(NavSettingActivity.this, R.style.AlertDialogCustom);
                alt_bld.setTitle("회원정보 삭제").setIcon(R.drawable.send).setMessage("회원 정보를 영구적으로 삭제하시겠습니까?").setCancelable(
                        false).setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                final String tempemail = user.getEmail();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener <Void>() {
                                            @Override
                                            public void onComplete( @NonNull Task <Void> task ) { //추후 다이얼로그 설정하여 한번 더 확인
                                                if (task.isSuccessful()) {
                                                    Log.d("회원정보 삭제", "User account deleted.");
                                                    Toast.makeText(NavSettingActivity.this, tempemail + "님의 정보가 영구적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 취소 클릭. dialog 닫기.
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();
            }
        });

        versionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {//버전정보
                android.app.FragmentManager fragmentManager = getFragmentManager();
                SettingVersionDialogFragment dialog = new SettingVersionDialogFragment();
                dialog.show(fragmentManager, "abc");
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {//도움말
            @Override
            public void onClick( View view ) {
                //도움말
            }
        });

        mancustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(NavSettingActivity.this, NavManagerActivity.class);
                startActivity(intent);

            }
        });

        manmanagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(NavSettingActivity.this, NavCustomerActivity.class);
                startActivity(intent);

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                FirebaseAuth user = FirebaseAuth.getInstance();
                FirebaseUser user1 = user.getCurrentUser();
                if (user1 != null) {///로그인 된 상태라면 //확인절차

                    FirebaseAuth.getInstance().signOut();//로그아웃
                    Toast.makeText(NavSettingActivity.this,user1.getDisplayName()+"님이 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        writePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(NavSettingActivity.this, BoardWriteActivity.class);
                intent.putExtra("CURRENT_BOARD_TAB", BoardTabFragment.getCurrentTab()-1);
                startActivity(intent);
            }
        });

    }


    public void findData(Query recentPostsQuery) {
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    user.setVisibility(View.VISIBLE);
                    manager.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                throw databaseError.toException();
            }
        });
    }

    public void findData2(Query recentPostsQuery) {
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    user.setVisibility(View.VISIBLE);
                    manager.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                throw databaseError.toException();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {//toolbar의 back키 눌렀을 때 동작
        switch (item.getItemId()){
            case android.R.id.home:{
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
