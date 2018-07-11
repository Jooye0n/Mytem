package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Board100000UpperFragment extends BoardCommonFragment {

    public Board100000UpperFragment() {}

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("10만원이상");
    }

    @Override
    public String getPostType() {
        return "10만원이상";
    }

}
