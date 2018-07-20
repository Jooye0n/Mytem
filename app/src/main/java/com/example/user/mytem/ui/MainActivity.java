package com.example.user.mytem.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar actionBar;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView textView;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_email_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header333);
        TextView nav_header_name_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header2);

        if(user != null) { //자동로그인 된 상태라면
            nav_header_email_text.setText(user.getEmail());
            nav_header_name_text.setText(user.getDisplayName());//표시해준다
        }

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_board_container, new BoardTabFragment()).commit();//지금 있는 메인의 fragment에 새로운 BoardTabfragment추가한다.

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseHandler.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected( MenuItem item ) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {//로그인
            android.app.FragmentManager fragmentManager = getFragmentManager();
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(fragmentManager, "LoginDialogFragment");

        } else if (id == R.id.nav_basket) {//장바구니
            Intent intent = new Intent(this, NavCartActicity.class);
            startActivity(intent);

        } else if (id == R.id.nav_list) {//구매목록
            Intent intent = new Intent(this, NavShopListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {//세팅
            Intent intent = new Intent(this,NavSettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {//로그아웃
            FirebaseAuth user = FirebaseAuth.getInstance();
            FirebaseUser user1 = user.getCurrentUser();
            if(user1!=null) {///로그인 된 상태라면 //확인절차
                FirebaseAuth.getInstance().signOut();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View nav_header_view = navigationView.getHeaderView(0);
                TextView nav_header_email_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header333);
                TextView nav_header_name_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header2);
                nav_header_email_text.setText("로그인이 필요한 서비스입니다.");
                nav_header_name_text.setText("");

                Toast.makeText(this,user1.getEmail()+"님이 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_customer_management) {
            Intent intent = new Intent(this,NavCustomerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manager_management) {
            Intent intent = new Intent(this,NavManagerActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}