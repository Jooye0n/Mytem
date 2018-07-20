package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerAFragment extends CustomerCommonTabFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("AUser");
    }

    @Override
    public String getPostType() {
        return "AUser";
    }

    public CustomerAFragment() {
    }
}
