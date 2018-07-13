package com.example.user.mytem.model;

import android.util.Log;

import com.example.user.mytem.singleton.CUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CUserModel {
    private DatabaseReference databaseReference;
    private List<CUser> mUsers = new ArrayList <>();


    public void addUserModel( CUser user) {
        mUsers.add(user);
    }

    public CUserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("CUser").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String pw = ds.getValue(CUser.class).getUpassword();
                    String phone = ds.getValue(CUser.class).getphone();
                    String rrn = ds.getValue(CUser.class).getUrrn();
                    String rrn2 = ds.getValue(CUser.class).getUrrn2();
                    String name = ds.getValue(CUser.class).getUserName();
                    String email = ds.getValue(CUser.class).getUemail();

                    CUser user = new CUser(name, pw, email, phone, rrn, rrn2);//post는 model에서 생성한다.
                    addUserModel(user);//여기서 가져와라의 형식 만들어줌+객체 생성은 model만 한다.
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }


    public void createUser(String userName, String upassword, String uemail, String uphone, String urrn, String urrn2) {
        Log.v("로그cccc","로그");
        databaseReference.child("CUser").
                push().setValue(CUser.newUser(userName, upassword, uemail, uphone, urrn, urrn2));
    }
}
