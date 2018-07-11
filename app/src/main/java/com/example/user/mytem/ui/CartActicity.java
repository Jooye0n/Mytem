package com.example.user.mytem.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CartActicity extends AppCompatActivity {

    private ActionBar actionBar;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        img = findViewById(R.id.imageButton2);

        selectStore();
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

    private void selectStore() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();

        DatabaseReference mStoreRef = mDatabase.child("생산&제조");

        mStoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // image loading
                StorageReference mStorageRef;
                mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" + "명" + ".jpg");
                Glide.with(CartActicity.this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

