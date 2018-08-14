package com.example.user.mytem.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.example.user.mytem.model.PostModel;
import com.example.user.mytem.singleton.Post;
import com.example.user.mytem.ui.BoardDetailActivity;
import com.example.user.mytem.ui.BoardTabFragment;
import com.example.user.mytem.ui.BoardWriteActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.Objects;

public class ArrayListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView titleTextView;
    private TextView contentsTextView;
    static private ImageView urlImageView;
    private TextView priceTextView;//일반소비자
    private TextView price2TextView;
    private TextView priceATextView;
    private TextView priceBTextView;
    private TextView numberTextView;//재고
    private ImageView cartImageView;
    private ImageView buyImageView;

    private ImageView dropdownButton;
    private String dataRefKey;
    private String postType;
    private String detail;
    private Context context;
    private DatabaseReference mDatabase;
    private Post post;
    private Boolean check = false;
    private String key;

    private PostModel postModel;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");//원화 단위로 글자를 변경시켜주는 부분(포맷설정)
    private String result = "";

    public ArrayListViewHolder( View itemView) {
        super(itemView);
        context = itemView.getContext();
        titleTextView = (TextView) itemView.findViewById(R.id.post_title_text_view);
        contentsTextView = (TextView) itemView.findViewById(R.id.post_contents_text_view);
        numberTextView = (TextView) itemView.findViewById(R.id.post_comment_number);//재고
        priceTextView = (TextView) itemView.findViewById(R.id.post_price_textView1);//소비자가
        price2TextView = (TextView) itemView.findViewById(R.id.post_price_textView2);
        priceATextView = itemView.findViewById(R.id.post_price_textView4);
        priceBTextView = itemView.findViewById(R.id.post_price_textView3);
        dropdownButton = (ImageView) itemView.findViewById(R.id.post_dropdown_button);//수정삭제
        dropdownButton.setVisibility(View.GONE);
        urlImageView = itemView.findViewById(R.id.post_imageButton);//사진

        cartImageView = itemView.findViewById(R.id.cart_btn);
        buyImageView = itemView.findViewById(R.id.buy_btn);

        postModel = new PostModel();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 뷰와 인스턴스 연결

        priceTextView.addTextChangedListener(new TextWatcher() {//EditText 의 글자 변경을 감지
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){ //EditText 가 비어있지 않을 때만 실행한다
                    result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                    priceTextView.setText(result);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });//화폐 단위 끊어주는 리스너 설정
        price2TextView.addTextChangedListener(new TextWatcher() {//EditText 의 글자 변경을 감지
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){ //EditText 가 비어있지 않을 때만 실행한다
                    result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                    price2TextView.setText(result);

                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });
        priceATextView.addTextChangedListener(new TextWatcher() {//EditText 의 글자 변경을 감지
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){ //EditText 가 비어있지 않을 때만 실행한다
                    result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                    priceATextView.setText(result);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });
        priceBTextView.addTextChangedListener(new TextWatcher() {//EditText 의 글자 변경을 감지
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){ //EditText 가 비어있지 않을 때만 실행한다
                    result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));

                    priceBTextView.setText(result);

                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

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



        itemView.setOnClickListener(this);//onClick을 말한다.
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) { //////등록된 상품 하나를 선택한 경우 상세 dialog 띄우기
        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();

        Intent intent = new Intent(context,BoardDetailActivity.class);
        intent.putExtra("POST_DETAIL", detail);
        intent.putExtra("POST_CATEGORY", post.getCategory());
        intent.putExtra("POST_BRAND", post.getBrand());
        intent.putExtra("POST_TITLE",post.getTitle());
        intent.putExtra("POST_CONTENTS",post.getContents());
        intent.putExtra("POST_MAJOR",post.getPriceB());
        intent.putExtra("POST_PRICE_ONE", post.getPrice());
        intent.putExtra("POST_PRICE_TWO", post.getPrice2());
        intent.putExtra("POST_PRICE_THREE", post.getPriceA());
        intent.putExtra("POST_PRODUCTION", post.getProduction());
        intent.putExtra("POST_ORIGIN", post.getOrigin());
        intent.putExtra("POST_DELIVERY1", post.getDelivery1());
        intent.putExtra("POST_DELIVERY2", post.getDelivery2());

        context.startActivity(intent);

        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
    }

    public void delectStoragePhoto() {
        Log.i("실행되는지보려고","4");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("albumImages/" +contentsTextView.getText().toString()+ ".jpg");

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("실행되는지보려고","5");

                Log.v("사진삭제성공",contentsTextView.getText().toString()+".jpg");
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("실행되는지보려고","6");

                Log.v("사진삭제실패",contentsTextView.getText().toString()+".jpg");
            }
        });
    }

    public void bindData(Post model) {
        // 뷰와 데이터 바인딩

        post = model;
        numberTextView.setText(String.valueOf(model.getNumber()));
        priceTextView.setText(String.valueOf(model.getPrice()));
        price2TextView.setText(String.valueOf(model.getPrice2()));
        priceATextView.setText(String.valueOf(model.getPriceA()));
        priceBTextView.setText(String.valueOf(model.getPriceB()));
        detail = model.getDetail();
        titleTextView.setText(model.getTitle());
        contentsTextView.setText(model.getContents());

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +contentsTextView.getText().toString()+ ".jpg");
        Log.v("로그",contentsTextView.getText().toString());//정상출력됨
        Glide.with(context).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(urlImageView);
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
        mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +contentsTextView.getText().toString()+ ".jpg");
        Log.v("로그",contentsTextView.getText().toString());//정상출력됨
        Glide.with(context).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(urlImageView);
    }


}
