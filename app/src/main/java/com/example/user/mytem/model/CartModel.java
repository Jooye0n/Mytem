package com.example.user.mytem.model;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.Post;
import com.example.user.mytem.viewholder.CartViewHolder;
import com.example.user.mytem.viewholder.ManagerPostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private DatabaseReference databaseReference;
    private List<Post> mUsers = new ArrayList<>();
    private String postType = "Cart";
    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }


    public void addPostModel( Post user) {
        mUsers.add(user);
    }

    public CartModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(postType).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String title = ds.getValue(Post.class).getTitle();
                    int number = ds.getValue(Post.class).getNumber();
                    int price = ds.getValue(Post.class).getPrice();
                    int price2 = ds.getValue(Post.class).getPrice2();
                    int priceA = ds.getValue(Post.class).getPriceA();
                    int priceB = ds.getValue(Post.class).getPriceB();
                    String content = ds.getValue(Post.class).getContents();
                    String detail = ds.getValue(Post.class).getDetail();
                    String category = ds.getValue(Post.class).getCategory();
                    String brand = ds.getValue(Post.class).getBrand();
                    String origin = ds.getValue(Post.class).getOrigin();
                    String production = ds.getValue(Post.class).getProduction();
                    String delivery1 = ds.getValue(Post.class).getDelivery1();
                    String delivery2 = ds.getValue(Post.class).getDelivery2();

                    Post post = new Post(title, number, price, price2, priceA, priceB, content,"url", detail,category, production, origin, brand, delivery1, delivery2);//post는 model에서 생성한다.
                    addPostModel(post);//여기서 가져와라의 형식 만들어줌+객체 생성은 model만 한다.
                }// String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }

    //원하는 데이터를 얻기 위해 데이터베이스에 정보를 요청(Request)하는 것=쿼리
    public FirebaseRecyclerAdapter setAdapter( Query query, final String postType) {

        FirebaseRecyclerAdapter<Post, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Post, CartViewHolder>(Post.class, R.layout.list_item_cart,
                CartViewHolder.class, query) {
            @Override
            protected void populateViewHolder( CartViewHolder viewHolder, Post model, int position ) {
                DatabaseReference postRef = getRef(position);//postion에 해당하는 ref를 가져와서
                String postKey = postRef.getKey();//key를 찾은후
                viewHolder.bindPost(model, postKey, postType);//post를 하나씩 할당
            }

        };
        return adapter;
    }
}
