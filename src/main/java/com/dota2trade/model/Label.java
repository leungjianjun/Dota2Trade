package com.dota2trade.model;

/**
 * Created by teireien on 14-1-1.
 * 标签
 */
public class Label {
    private int id;
    private String name;//标签名
    private int createid;//创建者

    public int getId(){return  this.id;}
    public void setId(int id){
        this.id = id;
    }
    public String getName(){return  this.name;}
    public void setName(String name){
        this.name = name;
    }
    public int getCreateid(){return this.createid;}
    public void setCreateid(int createid){
        this.createid = createid;
    }
}
