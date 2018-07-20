package com.example.user.mytem.model;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.SUser;
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

public class SUserModel {
    private DatabaseReference databaseReference;
    private List<SUser> mUsers = new ArrayList<>();
    private String postType = "SUser";
    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }


    public void addUserModel( SUser user) {
        mUsers.add(user);
    }

    public SUserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(postType).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String mname = ds.getValue(SUser.class).getMname();
                    String mposition = ds.getValue(SUser.class).getMposition();
                    String memail = ds.getValue(SUser.class).getMemail();
                    String mphone = ds.getValue(SUser.class).getMphone();
                    String mpw = ds.getValue(SUser.class).getMpassword();

                    SUser user = new SUser(mname, mposition, memail, mphone, mpw);//post는 model에서 생성한다.
                    addUserModel(user);//여기서 가져와라의 형식 만들어줌+객체 생성은 model만 한다.
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }

    // String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail
    public void writePost(String name, String position, String email, String phone, String pw) {
        databaseReference.child(postType).
                push().setValue(SUser.newSUer(name, position, email, phone, pw));
    }//post형태의 객체를 등록

    public void correctPost(String name, String position, String email, String phone, String pw, String postKey) {
        databaseReference.child(postType).
                child(postKey).setValue(SUser.newSUer(name, position, email, phone, pw));

    }//수정

    //원하는 데이터를 얻기 위해 데이터베이스에 정보를 요청(Request)하는 것=쿼리
    public FirebaseRecyclerAdapter setAdapter( Query query, final String postType) {

        FirebaseRecyclerAdapter<SUser, ManagerPostViewHolder> adapter = new FirebaseRecyclerAdapter<SUser, ManagerPostViewHolder>(SUser.class, R.layout.list_item_manager,
                ManagerPostViewHolder.class, query) {
            @Override
            protected void populateViewHolder( ManagerPostViewHolder viewHolder, SUser model, int position ) {
                DatabaseReference postRef = getRef(position);//postion에 해당하는 ref를 가져와서
                String postKey = postRef.getKey();//key를 찾은후
                viewHolder.bindPost(model, postKey);//post를 하나씩 할당
            }

        };
        return adapter;
    }
}
