package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.mytem.R;

public class NavCustomerActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_customer_container, new CustomerTabFragment()).commit();//지금 있는 메인의 fragment에 새로운 BoardTabfragment추가한다.
    }

    @Override
    public boolean onPrepareOptionsMenu( Menu menu) {///////////////쓰기 안보이게 (사용자는 작성할 수 없다.)
        super.onPrepareOptionsMenu(menu);

        MenuItem item3  = menu.findItem(R.id.menu_write);
        item3.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

