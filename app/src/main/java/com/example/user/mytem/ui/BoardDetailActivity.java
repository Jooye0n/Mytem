package com.example.user.mytem.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.user.mytem.R;
import android.support.v7.widget.Toolbar;

public class BoardDetailActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Button request;
    private Button cart;
    private Button buy;
    private TextView detailTextView;
    private TextView titleTextView;
    private android.widget.ImageButton cancel;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected( android.view.MenuItem item ) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}