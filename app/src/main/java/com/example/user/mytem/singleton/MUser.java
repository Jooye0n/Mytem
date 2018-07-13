package com.example.user.mytem.singleton;
/*
최고관리자
 */
public class MUser extends SUser {
    public MUser( String name, String position, String email, String phone, String pw ) {
        super(name, position, email, phone, pw);
    }

    @Override
    public String getManagerType() {
        return "MUser";
    }
}
