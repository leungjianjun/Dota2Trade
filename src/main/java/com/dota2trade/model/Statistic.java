package com.dota2trade.model;

/**
 * Created by Cancy on 14-1-6.
 */
public class Statistic {
    private int userId;
    private String account;
    private int literatureCount;
    private int attachmentCount;
    private int simpleCount;
    private int complexCount;
    private int period;// 0--全部 1--1周之内 2--1月之内 3--半年之内 4--1年之内

    public void setUserId(int userId){this.userId=userId;}
    public int getUserId(){return this.userId;}
}
