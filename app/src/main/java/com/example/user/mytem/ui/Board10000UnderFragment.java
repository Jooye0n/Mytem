package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Board10000UnderFragment extends BoardCommonFragment {

    public Board10000UnderFragment() {
    }

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("만원미만");
    }

    @Override
    public String getPostType() {
        return "만원미만";
    }
}
