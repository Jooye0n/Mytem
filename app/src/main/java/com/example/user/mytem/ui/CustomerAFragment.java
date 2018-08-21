package com.example.user.mytem.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerAFragment extends CustomerCommonTabFragment {

    @Override
    public DatabaseReference getRef() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference.child("AUser");
        //Query query = getRef().orderByChild("title").startAt(s).endAt(s + "\uf8ff");//입력한 문자열로 시작하는 title의 child만 list로 나열
        //                setAdapter(query);
    }

    @Override
    public String getPostType() {
        return "AUser";
    }

    public CustomerAFragment() {
    }
}
