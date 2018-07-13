package com.example.user.mytem.singleton;

public class AUser extends BUser {
    public AUser( String userName, String upassword, String uemail, String uphone, String urrn, String urrn2 ) {
        super(userName, upassword, uemail, uphone, urrn, urrn2);
    }

    @Override
    public String getUserType() {
        return "AUser";
    }
}
