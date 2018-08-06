package com.example.user.mytem.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private BackPressCloseHandler backPressCloseHandler;
    private FloatingActionButton noticeFloatingActionButton;
    private TextView noticeCount;
    private LinearLayout myPointLinearLayout;
    private LinearLayout myClasstLinearLayout;
    private ConstraintLayout myNoticeConstraintLayout;
    private Button navLoginButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        view  bind
         */

        if(CheckAppFirstExecute()==true) { //최초실행인 경우
            user = null;
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_bnv);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_email_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header_email);
        TextView nav_header_name_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header_name);

        myPointLinearLayout = nav_header_view.findViewById(R.id.myPoint);
        myClasstLinearLayout = nav_header_view.findViewById(R.id.myClass);
        myNoticeConstraintLayout = nav_header_view.findViewById(R.id.myNotice);

        noticeFloatingActionButton = nav_header_view.findViewById(R.id.notice_floating);
        noticeCount = nav_header_view.findViewById(R.id.notice_count);

        navLoginButton = nav_header_view.findViewById(R.id.nav_login_btn);
        ImageView navSettingButton = nav_header_view.findViewById(R.id.nav_setting_btn);

        LinearLayout cartButton = nav_header_view.findViewById(R.id.nav_btn_cart);
        LinearLayout buyListButton = nav_header_view.findViewById(R.id.nav_btn_buy);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//뒤로가기버튼만 생성(기능없음)
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_board_container, new BoardTabFragment()).commit();//지금 있는 메인의 fragment에 새로운 BoardTabfragment추가한다.



        /*
        view visibility
         */

        noticeFloatingActionButton.setVisibility(View.INVISIBLE);
        noticeCount.setVisibility(View.INVISIBLE);
        navLoginButton.setVisibility(View.VISIBLE);
        nav_header_name_text.setVisibility(View.GONE);//로그인이 안된 상태면

        if(user != null) { //자동로그인 된 상태라면 CheckAppFirstExecute()==true면 최초실행
            nav_header_email_text.setText(user.getEmail());
            nav_header_name_text.setVisibility(View.VISIBLE);
            nav_header_name_text.setText(user.getDisplayName()+"님");//표시해준다
            nav_header_name_text.setTextColor(Color.parseColor("#072972"));
            nav_header_name_text.setPaintFlags(nav_header_name_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//밑줄(링크)

            noticeFloatingActionButton.setVisibility(View.VISIBLE);
            noticeCount.setVisibility(View.VISIBLE);

            noticeCount.setText("2");//차후에 알림 카운트 개별 변경

            navLoginButton.setVisibility(View.INVISIBLE);
        }


        /*
        view event
         */

        nav_header_name_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                if(user1!=null) {///로그인 된 상태라면 //확인절차
                    Intent intent = new Intent(MainActivity.this,BottomMypageActivity.class);
                    startActivity(intent);
                }
            }
        });

        View.OnClickListener onClickHeader = new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                onClickHeader();
            }
        };

        myPointLinearLayout.setOnClickListener(onClickHeader);
        myClasstLinearLayout.setOnClickListener(onClickHeader);
        myNoticeConstraintLayout.setOnClickListener(onClickHeader);

        navLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                LoginDialogFragment dialog = new LoginDialogFragment();
                dialog.show(fragmentManager, "LoginDialogFragment");
            }
        });

        navSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(MainActivity.this, NavSettingActivity.class);
                startActivity(intent);
            }
        });

        nav_header_email_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                LoginDialogFragment dialog = new LoginDialogFragment();
                dialog.show(fragmentManager, "LoginDialogFragment");
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(MainActivity.this,NavCartActicity.class);
                startActivity(intent);
            }
        });

        buyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(MainActivity.this,NavShopListActivity.class);
                startActivity(intent);
            }
        });



        /*
        view back handling
         */

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public boolean CheckAppFirstExecute(){
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }

        return !isFirst;
    }


    @Override
    protected void onStart() {
        super.onStart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_name_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header_name);
        TextView nav_header_email_text = (TextView) nav_header_view.findViewById(R.id.nav_sub_header_email);

        noticeFloatingActionButton.setVisibility(View.INVISIBLE);
        noticeCount.setVisibility(View.INVISIBLE);
        navLoginButton.setVisibility(View.VISIBLE);
        nav_header_email_text.setText("로그인이 필요한 서비스입니다.");
        nav_header_name_text.setVisibility(View.GONE);//로그인이 안된 상태면

        FirebaseAuth user = FirebaseAuth.getInstance();
        FirebaseUser user1 = user.getCurrentUser();
        if(user1 != null) { //자동로그인 된 상태라면
            nav_header_email_text.setText(user1.getEmail());
            nav_header_name_text.setVisibility(View.VISIBLE);
            nav_header_name_text.setText(user1.getDisplayName()+"님");//표시해준다
            nav_header_name_text.setTextColor(Color.parseColor("#072972"));
            nav_header_name_text.setPaintFlags(nav_header_name_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//밑줄(링크)

            noticeFloatingActionButton.setVisibility(View.VISIBLE);
            noticeCount.setVisibility(View.VISIBLE);

            noticeCount.setText("2");//차후에 알림 카운트 개별 변경

            navLoginButton.setVisibility(View.INVISIBLE);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_one://검색

                    return true;
                case R.id.action_two://공유

                    return true;
                case R.id.action_three://QR코드

                    return true;
                case R.id.action_four://리뷰

                    return true;
                case R.id.action_five://마이페이지
                    FirebaseAuth user = FirebaseAuth.getInstance();
                    FirebaseUser user1 = user.getCurrentUser();
                    if(user1!=null) {///로그인 된 상태라면 //확인절차
                        Intent boardIntent = new Intent(MainActivity.this, BottomMypageActivity.class);
                        startActivity(boardIntent);
                        // toolbarText.setText("HOME");
                        return true;
                    } else {
                        android.app.FragmentManager fragmentManager = getFragmentManager();
                        LoginDialogFragment dialog = new LoginDialogFragment();
                        dialog.show(fragmentManager, "LoginDialogFragment");
                        return true;
                    }
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseHandler.onBackPressed();
        }
    }

    public void onClickHeader() {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if(user1!=null) {//로그인 되어있으면
            Intent intent = new Intent(MainActivity.this, BottomMypageActivity.class);
            startActivity(intent);
        } else {//아니면
            android.app.FragmentManager fragmentManager = getFragmentManager();
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(fragmentManager, "LoginDialogFragment");
        }
    }

}