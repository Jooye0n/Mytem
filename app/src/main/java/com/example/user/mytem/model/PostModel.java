package com.example.user.mytem.model;


import android.util.Log;


import com.example.user.mytem.R;
import com.example.user.mytem.singleton.Post;
import com.example.user.mytem.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PostModel {
    private DatabaseReference databaseReference;
    private OnDataChangedListener onDataChangedListener;
    final String CART_TYPE = "장바구니";

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public PostModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("임시").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public PostModel(String postType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(postType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


// String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail
    public void writePost( String postType, String url, String title, String contents, int number, int price, int price2, int priceA, int priceB,  String detail,
                           String category, String production, String origin, String brand, String delivery1, String delivery2) {
        databaseReference.child(postType).
                push().setValue(Post.newPost(title, number, price, price2, priceA, priceB, contents, url, detail,
                category, production, origin, brand, delivery1, delivery2));
        Log.i("실행되는지보려고","10");

    }//post형태의 객체를 등록

//    public void writeCart(String userType, String postType, int count, String title, String contents, int number, int price, int price2, int priceA, int priceB,  String detail,
//                          String category, String production, String origin, String brand, String delivery1, String delivery2) {
//        databaseReference.child(userType).child(postType).child(CART_TYPE).
//                push().setValue(Post.newCart(title, number, price, price2, priceA, priceB, contents, count, detail,
//                category, production, origin, brand, delivery1, delivery2));
//
//        Log.i("실행되는지보려고","10");
//
//    }//post형태의 객체를 등록

    public void correctPost( String postType, String url, String title, String contents, String postKey, int number, int price, int price2, int priceA, int priceB, String detail,
                             String category, String production, String origin, String brand, String delivery1, String delivery2) {
        databaseReference.child(postType).
                child(postKey).setValue(Post.newPost(title, number, price, price2, priceA, priceB, contents, url, detail
                ,category, production, origin, brand, delivery1, delivery2));
        Log.i("실행되는지보려고","11");
    }//수정

//    public void correctCart( String userType, String postType, int count, String title, String contents, String postKey, int number, int price, int price2, int priceA, int priceB, String detail,
//                             String category, String production, String origin, String brand, String delivery1, String delivery2) {
//        databaseReference.child(userType).child(CART_TYPE).
//                child(postKey).setValue(Post.newCart(title, number, price, price2, priceA, priceB, contents, count, detail,
//                category, production, origin, brand, delivery1, delivery2));
//        Log.i("실행되는지보려고","11");
//    }//수정



    //원하는 데이터를 얻기 위해 데이터베이스에 정보를 요청(Request)하는 것=쿼리
    public FirebaseRecyclerAdapter setAdapter( Query query, final String postType) {
        Log.i("실행되는지보려고","9");


        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.list_item_post,
                PostViewHolder.class, query) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                Log.i("실행되는지보려고","12");

                DatabaseReference postRef = getRef(position);//postion에 해당하는 ref를 가져와서
                String postKey = postRef.getKey();//key를 찾은후
                viewHolder.bindPost(model, postKey, postType);//post를 하나씩 할당
            }
        };
        return adapter;
    }
}
