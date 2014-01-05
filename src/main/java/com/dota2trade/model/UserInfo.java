package com.dota2trade.model;

/**
 * Created by teireien on 14-1-5.
 */
public class UserInfo {
    private int id;
    private int userid;
    private String account;
    private String name;
    private String major;
    private String email;

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
