package com.example.user.mytem.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.example.user.mytem.ui.BoardTabFragment;
import com.example.user.mytem.ui.BoardWriteActivity;
import com.example.user.mytem.ui.PostDetailDialogFragment;
import com.example.user.mytem.singleton.Post;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Objects;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView titleTextView;
    private TextView contentsTextView;
    static private ImageView urlImageView;
    private TextView priceTextView;//일반소비자
    private TextView price2TextView;
    private TextView priceATextView;
    private TextView priceBTextView;
    private TextView numberTextView;//재고

    private ImageView dropdownButton;
    private String dataRefKey;
    private String postType;
    private String detail;
    private Context context;
    private DatabaseReference mDatabase;
    private Post post;

    public PostViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        titleTextView = (TextView) itemView.findViewById(R.id.post_title_text_view);
        contentsTextView = (TextView) itemView.findViewById(R.id.post_contents_text_view);
        numberTextView = (TextView) itemView.findViewById(R.id.post_comment_number);//재고
        priceTextView = (TextView) itemView.findViewById(R.id.price_textView1);//소비자가
        price2TextView = (TextView) itemView.findViewById(R.id.price_textView2);
        priceATextView = itemView.findViewById(R.id.price_textView4);
        priceBTextView = itemView.findViewById(R.id.price_textView3);
        dropdownButton = (ImageView) itemView.findViewById(R.id.dropdown_button);//수정삭제
        urlImageView = itemView.findViewById(R.id.imageButton);//사진
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.i("실행되는지보려고","8");




        //selectStore();

        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, dropdownButton);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete) {//////////////////////////////////////////////////삭제->사진 삭제 해야한다.
                            if (Objects.equals(post.getAuthorUid(), "관리자")) {
                                mDatabase.child(postType).child(dataRefKey).removeValue();//firebase에서 삭제
                                // Create a storage reference from our app
                                delectStoragePhoto();


                            } else {
                                Snackbar.make(numberTextView, "권한이 없습니다", Snackbar.LENGTH_SHORT).show();//작성자가 아닌데 삭제시도하는경우->혹시나
                            }
                        } else if (item.getItemId() == R.id.popup_rewrite) {///////////////////////////////////////////수정
                            delectStoragePhoto();
                            Log.i("실행되는지보려고","7");

                            Intent intent = new Intent(context, BoardWriteActivity.class);
                            intent.putExtra("CURRENT_BOARD_TAB", BoardTabFragment.getCurrentTab()-1);
                            intent.putExtra("BOARD_TITLE", post.getTitle());
                            intent.putExtra("BOARD_CONTENTS", post.getContents());
                            intent.putExtra("CORRECT_POST_KEY", dataRefKey);
                            intent.putExtra("BOARD_DETAIL",post.getDetail());
                            intent.putExtra("BOARD_NUM",post.getNumber());
                            intent.putExtra("BOARD_PRICE",post.getPrice());
                            intent.putExtra("BOARD_PRICE2", post.getPrice2());
                            intent.putExtra("BOARD_PRICEA", post.getPriceA());
                            intent.putExtra("BOARD_PRICEB", post.getPriceB());
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        itemView.setOnClickListener(this);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) { //////등록된 상품 하나를 선택한 경우 상세 dialog 띄우기
        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString("POST_DATAIL", detail);
        arguments.putString("POST_TITLE",post.getTitle());

        PostDetailDialogFragment dialog = new PostDetailDialogFragment();
        dialog.setArguments(arguments);

        dialog.show(fragmentManager, "POST_DATAIL");
        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
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

        int visibility = Objects.equals(model.getAuthorUid(), "관리자") ?
                View.VISIBLE : View.GONE;//댓글 단 사람이 삭제하려는 사람과 같으면 보인다->지우기

        dropdownButton.setVisibility(visibility);

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

    public void delectStoragePhoto() {
        Log.i("실행되는지보려고","4");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("albumImages/" +titleTextView.getText().toString()+ ".jpg");

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("실행되는지보려고","5");

                Log.v("사진삭제성공",titleTextView.getText().toString()+".jpg");
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("실행되는지보려고","6");

                Log.v("사진삭제실패",titleTextView.getText().toString()+".jpg");
            }
        });
    }


}
