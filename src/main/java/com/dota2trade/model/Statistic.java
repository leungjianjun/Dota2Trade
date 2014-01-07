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
    //private int period;// 0--全部 1--1周之内 2--1月之内 3--半年之内 4--1年之内

    public void setUserId(int userId){this.userId=userId;}
    public int getUserId(){return this.userId;}
    public void setAccount(String account){this.account=account;}
    public String getAccount(){return this.account;}
    public void setLiteratureCount(int literatureCount){this.literatureCount=literatureCount;}
    public int getLiteratureCount(){return this.literatureCount;}
    public void setAttachmentCount(int attachmentCount){this.attachmentCount=attachmentCount;}
    public int getAttachmentCount(){return this.attachmentCount;}
    public void setSimpleCount(int simpleCount){this.simpleCount=simpleCount;}
    public int getSimpleCount(){return this.simpleCount;}
    public void setComplexCount(int complexCount){this.complexCount=complexCount;}
    public int getComplexCount(){return this.complexCount;}
//    public void setPeriod(int period){this.period=period;}
//    public int getPeriod(){return this.period;}

}
