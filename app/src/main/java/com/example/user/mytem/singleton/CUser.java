package com.example.user.mytem.singleton;

public class CUser {//일반 user

    private String userName;
    private String upassword;
    private String uemail;
    private boolean disabled; //사용자가 중지되었는지 여부. 중지된 경우 true, 사용 설정된 경우 false. 제공하지 않은 경우 기본값은 false.
    private String uphone;
    private String urrn;
    private String urrn2;

    private CUser() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public CUser( String userName, String upassword, String uemail, String uphone, String urrn, String urrn2) {
        this.urrn = urrn;
        this.upassword = upassword;
        this.disabled = true;
        this.uemail = uemail;
        this.userName = userName;
        this.uphone = uphone;
        this.urrn2 = urrn2;
    }

    public static CUser newUser( String userName, String upassword , String uemail, String uphone, String urrn, String urrn2) {
        return new CUser(userName, upassword, uemail, uphone, urrn, urrn2);
    }
    public String getUemail() {
        return uemail;
    }

    public String getUphone() {
        return uphone;
    }

    public String getUserName() {
        return userName;
    }

    public String getUpassword() {
        return upassword;
    }

    public String getUrrn() {
        return urrn;
    }

    public String getUrrn2() {
        return urrn2;
    }

    public String getUserType() {
        return "CUser";
    }

//    public boolean isDisabled() {
//        return disabled;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public void setDisabled(boolean disabled) {
//        this.disabled = disabled;
//    }
//
//    public void setUphone( String address) {
//        this.uphone = address;
//    }
//
//    public void setUpassword(String pw) {
//        this.upassword = pw;
//    }
//
//    public void setUrrn(String rrn) {
//        this.urrn = rrn;
//    }
//
//    public void setUemail( String uemail ) {
//        this.uemail = uemail;
//    }

    private static class Singleton {
        private static final CUser INSTANCE = new CUser();
    }

    public static CUser getInstance() {
        return Singleton.INSTANCE;
    }


}
