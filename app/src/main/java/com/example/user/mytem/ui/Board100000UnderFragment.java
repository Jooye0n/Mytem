package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Board100000UnderFragment extends CommonTabFragment {

    public Board100000UnderFragment() {}

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("10만원미만");
    }

    @Override
    public String getPostType() {
        return "10만원미만";
    }

}
