package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Board30000UnderFragment extends CommonTabFragment {

    public Board30000UnderFragment() {
    }

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("3만원미만");
    }

    @Override
    public String getPostType() {
        return "3만원미만";
    }
}
