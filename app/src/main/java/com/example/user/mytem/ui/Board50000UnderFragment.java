package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Board50000UnderFragment extends BoardCommonFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("5만원미만");
    }

    @Override
    public String getPostType() {
        return "5만원미만";
    }

    public Board50000UnderFragment() {
    }
}