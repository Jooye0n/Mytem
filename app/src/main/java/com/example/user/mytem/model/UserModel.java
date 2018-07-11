package com.example.user.mytem.model;

import android.util.Log;

import com.example.user.mytem.singleton.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserModel {
    private DatabaseReference databaseReference;
    private List<User> mUsers = new ArrayList <>();


    public void addUserModel(User user) {
        mUsers.add(user);
    }

    public UserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("일반").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getValue(User.class).getUid();
                    String pw = ds.getValue(User.class).getUpassword();
                    String phone = ds.getValue(User.class).getphone();
                    String rrn = ds.getValue(User.class).getUrrn();
                    String rrn2 = ds.getValue(User.class).getUrrn2();
                    String name = ds.getValue(User.class).getUserName();
                    String email = ds.getValue(User.class).getUemail();

                    User user = new User(id, name, pw, email, phone, rrn, rrn2);
                    addUserModel(user);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }


    public void createUser(String uid, String userName, String upassword, String uemail, String uphone, String urrn, String urrn2) {
        Log.v("로그cccc","로그");
        databaseReference.child("일반").
                push().setValue(User.newUser(uid, userName, upassword, uemail, uphone, urrn, urrn2));
    }
}
