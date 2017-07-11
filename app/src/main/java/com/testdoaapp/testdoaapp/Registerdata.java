package com.testdoaapp.testdoaapp;

/**
 * Created by zealous on 4/7/17.
 */

public class Registerdata {

    int id;
    String email_id;
    String mobile_number;
    String password;

    public Registerdata() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }


    public Registerdata(int id, String email_id, String mobile_number) {
        this.id = id;
        this.email_id = email_id;
        this.mobile_number = mobile_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
