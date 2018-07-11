package com.example.user.mytem.model;

import android.widget.ImageView;

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

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
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

    public void writePost( String postType, String url, String title, String contents, int number, int price, String detail) {
        databaseReference.child(postType).
                push().setValue(Post.newPost(title, number, price, contents, url, detail));
    }//post형태의 객체를 등록

    public void correctPost( String postType, String url, String title, String contents, String postKey, int number, int price, String detail) {
        databaseReference.child(postType).
                child(postKey).setValue(Post.newPost(title, number, price, contents, url, detail));

    }//수정

    //원하는 데이터를 얻기 위해 데이터베이스에 정보를 요청(Request)하는 것=쿼리
    public FirebaseRecyclerAdapter setAdapter( Query query, final String postType) {

        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.list_item_post,
                PostViewHolder.class, query) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                DatabaseReference postRef = getRef(position);//postion에 해당하는 ref를 가져와서
                String postKey = postRef.getKey();//key를 찾은후
                viewHolder.bindPost(model, postKey, postType);//post를 하나씩 할당
            }
        };
        return adapter;
    }
}
