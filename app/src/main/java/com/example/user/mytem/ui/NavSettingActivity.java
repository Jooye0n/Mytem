package com.example.user.mytem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavSettingActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);


        Button modifyButton = findViewById(R.id.modify_btn);//회원 정보 수정
        Button deleteButton = findViewById(R.id.delete_btn);//회원 정보 삭제
        Button versionButton = findViewById(R.id.version_button);//버전정보
        Button queryButton = findViewById(R.id.query_button);//도움말
        Button mancustButton = findViewById(R.id.manager_custlist);//고객목록
        Button manmanagerButton = findViewById(R.id.manager_manlist);//관리자목록
        final Button logoutButton = findViewById(R.id.logout_btn);//로그아웃버튼

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {//회원정보 변경

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {//////////////////////////////////////////회원정보 삭제
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

                    Intent logoutintent = new Intent();
                    logoutintent.setAction("com.example.user.mytem");
                    sendBroadcast(logoutintent);//broadcast
                }
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
