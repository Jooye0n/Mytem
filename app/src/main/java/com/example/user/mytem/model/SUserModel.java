package com.example.user.mytem.model;

import android.util.Log;

import com.example.user.mytem.singleton.CUser;
import com.example.user.mytem.singleton.SUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SUserModel {
    private DatabaseReference databaseReference;
    private List<SUser> mUsers = new ArrayList<>();


    public void addUserModel( SUser user) {
        mUsers.add(user);
    }

    public SUserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("SUser").addValueEventListener(new ValueEventListener() {

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


    public void createUser(String name, String position, String email, String phone, String pw) {
        Log.v("로그cccc","로그");
        databaseReference.child("SUser").
                push().setValue(SUser.newSUer(name, position, email, phone, pw));
    }
}
