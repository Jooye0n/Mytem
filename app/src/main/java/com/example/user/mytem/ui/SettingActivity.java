package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.user.mytem.R;

public class SettingActivity extends AppCompatActivity {

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

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
               //회원정보 수정
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                //회원정보 삭제
            }
        });

        versionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//버전정보
                android.app.FragmentManager fragmentManager = getFragmentManager();
                VersionDialogFragment dialog = new VersionDialogFragment();
                dialog.show(fragmentManager, "abc");
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {//도움말
            @Override
            public void onClick(View view) {
            //도움말
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
