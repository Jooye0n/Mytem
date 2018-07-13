package com.example.user.mytem.model;

import android.util.Log;

import com.example.user.mytem.singleton.AUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AUserModel extends BUserModel {

    private DatabaseReference databaseReference;
    private List<AUser> mUsers = new ArrayList<>();

    public void addAUserModel( AUser user) {
        mUsers.add(user);
    }


    public AUserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AUser").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String pw = ds.getValue(AUser.class).getUpassword();
                    String phone = ds.getValue(AUser.class).getphone();
                    String rrn = ds.getValue(AUser.class).getUrrn();
                    String rrn2 = ds.getValue(AUser.class).getUrrn2();
                    String name = ds.getValue(AUser.class).getUserName();
                    String email = ds.getValue(AUser.class).getUemail();

                    AUser user = new AUser(name, pw, email, phone, rrn, rrn2);
                    addAUserModel(user);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }

    @Override
    public void createUser(String userName, String upassword, String uemail, String uphone, String urrn, String urrn2) {
        Log.v("로그aaaa","로그");
        databaseReference.child("AUser").
                push().setValue(AUser.newUser(userName, upassword, uemail, uphone, urrn, urrn2));
    }
}
