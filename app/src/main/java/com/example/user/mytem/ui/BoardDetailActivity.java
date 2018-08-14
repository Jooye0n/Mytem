package com.example.user.mytem.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.support.v7.widget.Toolbar;

import java.text.DecimalFormat;


public class BoardDetailActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Button bottom_request;
    private Button bottom_cart;
    private Button bottom_buy;
    private ImageView mainImg;

    TextView majorPrice;
    TextView firstPrice;
    TextView secPrice;
    TextView thirPrice;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");//원화 단위로 글자를 변경시켜주는 부분(포맷설정)
    private String result = "";

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        TextView category = findViewById(R.id.detail_category);
        TextView brand = findViewById(R.id.detail_brand);
        TextView title = findViewById(R.id.detail_title);
        TextView contents = findViewById(R.id.detail_contents);
        majorPrice = findViewById(R.id.major_price);
        firstPrice = findViewById(R.id.detail_price1);
        secPrice = findViewById(R.id.detail_price2);
        thirPrice = findViewById(R.id.detail_price3);
        TextView production = findViewById(R.id.production);
        TextView origin = findViewById(R.id.origin);
        TextView delivery1 = findViewById(R.id.delivery1);
        TextView delivery2 = findViewById(R.id.textView43);
        Button request = findViewById(R.id.btn_request);
        Button cart = findViewById(R.id.btn_cart);
        Button buy = findViewById(R.id.btn_buy);
        mainImg = findViewById(R.id.detail_img);


        bottom_buy = findViewById(R.id.btn_buy2);
        bottom_cart = findViewById(R.id.btn_cart2);
        bottom_request = findViewById(R.id.btn_request2);


        category.setText(getIntent().getExtras().getString("POST_CATEGORY"));
        brand.setText(getIntent().getExtras().getString("POST_BRAND"));
        majorPrice.setText(String.valueOf(getIntent().getExtras().getInt("POST_MAJOR")));
        title.setText(getIntent().getExtras().getString("POST_TITLE"));
        contents.setText(getIntent().getExtras().getString("POST_CONTENTS"));
        firstPrice.setText(String.valueOf(getIntent().getExtras().getInt("POST_PRICE_ONE")));
        secPrice.setText(String.valueOf(getIntent().getExtras().getInt("POST_PRICE_TWO")));
        thirPrice.setText(String.valueOf(getIntent().getExtras().getInt("POST_PRICE_THREE")));
        production.setText(getIntent().getExtras().getString("POST_PRODUCTION"));
        origin.setText(getIntent().getExtras().getString("POST_ORIGIN"));
        delivery1.setText(getIntent().getExtras().getString("POST_DELIVERY1"));
        delivery2.setText(getIntent().getExtras().getString("POST_DELIVERY2"));


        result = decimalFormat.format(Double.parseDouble(majorPrice.getText().toString().replaceAll(",","")));
        majorPrice.setText(result);

        result = decimalFormat.format(Double.parseDouble(firstPrice.getText().toString().replaceAll(",","")));
        firstPrice.setText(result);

        result = decimalFormat.format(Double.parseDouble(secPrice.getText().toString().replaceAll(",","")));
        secPrice.setText(result);

        result = decimalFormat.format(Double.parseDouble(thirPrice.getText().toString().replaceAll(",","")));
        thirPrice.setText(result);

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +title.getText().toString()+ ".jpg");
        Log.v("로그",title.getText().toString());//정상출력됨
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(mainImg);


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_board_container, new BoardDetailTabFragment()).commit();//지금 있는 메인의 fragment에 새로운 BoardTabfragment추가한다.

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