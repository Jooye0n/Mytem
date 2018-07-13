package com.example.user.mytem.model;

import android.util.Log;

import com.example.user.mytem.singleton.BUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BUserModel extends CUserModel{

    private DatabaseReference databaseReference;
    private List<BUser> mUsers = new ArrayList<>();

    public void addBUserModel( BUser user) {
        mUsers.add(user);
    }


    public BUserModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("BUser").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String pw = ds.getValue(BUser.class).getUpassword();
                    String phone = ds.getValue(BUser.class).getphone();
                    String rrn = ds.getValue(BUser.class).getUrrn();
                    String rrn2 = ds.getValue(BUser.class).getUrrn2();
                    String name = ds.getValue(BUser.class).getUserName();
                    String email = ds.getValue(BUser.class).getUemail();

                    BUser user = new BUser(name, pw, email, phone, rrn, rrn2);
                    addBUserModel(user);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        });

    }

    @Override
    public void createUser(String userName, String upassword, String uemail, String uphone, String urrn, String urrn2) {
        Log.v("로그bbbb","로그");
        databaseReference.child("BUser").
                push().setValue(BUser.newUser( userName, upassword, uemail, uphone, urrn, urrn2));
    }
}
