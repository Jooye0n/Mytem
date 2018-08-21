package com.example.user.mytem.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BoardDetail1Fragment extends BoardDetailTabFragment{

    private String contents;
    private TextView detail;
    private ImageButton detailImage;
    private String detailString;
    private String contentsString;

    public BoardDetail1Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_detail1, container, false);
        detail = view.findViewById(R.id.comment_detail_text);
        detailImage = view.findViewById(R.id.detailImage);

        detailString = getArguments().getString("Detail");
        contentsString = getArguments().getString("Contents");

        detail.setText(detailString);
        Log.v("DetailDetailDetailDetail", detailString);
        Log.v("DetailDetailDetailDetail", contentsString);//정상출력된다

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +contentsString+"_detail"+ ".jpg");
        Log.v("로그신주연","albumImages/" +contentsString+"_detail"+ ".jpg");//정상출력됨
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(detailImage);

        return view;
    }
}
