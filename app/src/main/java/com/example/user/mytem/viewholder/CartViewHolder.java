package com.example.user.mytem.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.example.user.mytem.model.PostModel;
import com.example.user.mytem.singleton.Post;
import com.example.user.mytem.ui.BoardDetailActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final String POSTTYPE_CART = "장바구니";

    private TextView titleTextView;
    private TextView contentsTextView;
    static private ImageView urlImageView;
    private TextView priceTextView;//일반소비자
    private TextView price2TextView;
    private TextView priceATextView;
    private TextView priceBTextView;
    private TextView numberTextView;//재고

    private String dataRefKey;
    private String postType;
    private String detail;
    private Context context;
    private DatabaseReference mDatabase;
    private Post post;
    private CheckBox checkBox;

    private PostModel postModel;

    public CartViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        titleTextView = (TextView) itemView.findViewById(R.id.cart_title_text_view);
        contentsTextView = (TextView) itemView.findViewById(R.id.cart_contents_text_view);
        numberTextView = (TextView) itemView.findViewById(R.id.cart_comment_number);//재고
        priceTextView = (TextView) itemView.findViewById(R.id.cart_price_textView1);//소비자가
        price2TextView = (TextView) itemView.findViewById(R.id.cart_price_textView2);
        priceATextView = itemView.findViewById(R.id.cart_price_textView4);
        priceBTextView = itemView.findViewById(R.id.cart_price_textView3);
        urlImageView = itemView.findViewById(R.id.imageButton);//사진
        checkBox = itemView.findViewById(R.id.checkbox_cart);//checkbox

        postModel = new PostModel();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) { //등록된 상품 하나를 선택한 경우 상세 activity 띄우기

        Intent intent = new Intent(context,BoardDetailActivity.class);
        intent.putExtra("POST_DATAIL", detail);
        intent.putExtra("POST_TITLE",post.getTitle());
        context.startActivity(intent);

        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);//애니메이션
    }

    public void bindPost( final Post model, String postKey, String postType) {
        post = model;
        numberTextView.setText(String.valueOf(model.getNumber()));
        priceTextView.setText(String.valueOf(model.getPrice()));
        price2TextView.setText(String.valueOf(model.getPrice2()));
        priceATextView.setText(String.valueOf(model.getPriceA()));
        priceBTextView.setText(String.valueOf(model.getPriceB()));
        this.dataRefKey = postKey;
        this.postType = postType;
        this.detail = model.getDetail();


        titleTextView.setText(model.getTitle());
        contentsTextView.setText(model.getContents());
        Log.i("string", String.valueOf(model.getPrice()));
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabase = database.getReference();

        // 안쓰면 각 post마다 이미지가 아예 없다
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +titleTextView.getText().toString()+ ".jpg");
        Log.v("로그",titleTextView.getText().toString());//정상출력됨
        Glide.with(context).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(urlImageView);
    }

}
