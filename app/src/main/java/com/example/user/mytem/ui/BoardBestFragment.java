package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BoardBestFragment extends BoardCommonFragment {

    public BoardBestFragment() {}

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("Best");
    }

    @Override
    public String getPostType() {
        return "Best";
    }

}
