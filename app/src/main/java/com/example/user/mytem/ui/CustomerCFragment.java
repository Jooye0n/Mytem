package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerCFragment extends CustomerCommonTabFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("CUser");
    }

    @Override
    public String getPostType() {
        return "CUser";
    }

    public CustomerCFragment() {
    }
}
