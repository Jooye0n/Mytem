package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerBFragment extends CommonTabFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("BUser");
    }

    @Override
    public String getPostType() {
        return "BUser";
    }

    public CustomerBFragment() {
    }
}
