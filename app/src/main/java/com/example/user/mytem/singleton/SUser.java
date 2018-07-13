package com.example.user.mytem.singleton;


/*
sub 관리자
 */

public class SUser {
    private String mname;
    private String mposition;
    private String memail;
    private String mphone;
    private String mpassword;

    public SUser() {
        //default
    }

    public SUser(String name, String position, String email, String phone, String pw) {
        this.mname = name;
        this.mposition = position;
        this.memail = email;
        this.mphone = phone;
        this.mpassword = pw;
    }

    public static SUser newSUer(String name, String position, String email, String phone, String pw) {
        return new SUser(name, position, email, phone, pw);
    }

    public String getMname() {
        return mname;
    }

    public String getMposition() {
        return mposition;
    }

    public String getMemail() {
        return memail;
    }

    public String getMphone() {
        return mphone;
    }

    public String getMpassword() {
        return mpassword;
    }

    public String getManagerType() {
        return "SUser";
    }

}
