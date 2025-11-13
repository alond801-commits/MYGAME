package com.idodrori.mygame.modle;

public class User {

    protected String id;

    protected String fname;

    protected String lname;

    protected String email;

    protected String phone;

    protected String password;

    public User() {
    }

    public User(String id, String fname, String lname, String email, String phone, String password) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
