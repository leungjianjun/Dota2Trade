package com.dota2trade.model;


import java.util.Date;

/**
 * Created by Cancy on 14-1-1.
 * 简单评价的model
 * 允许用户对同一文献发表多次评论，故加入时间属性
 */
public class Comment {
    private int id;
    private int commenterId;
    private String commenter;
    private int literatureId;
    private String literatureTitle;
    private String shortContent;
    private int score;
    private int status;// 0 draft 1 normal
    private String commentTime;

    public void setId(int id){this.id=id;}
    public int getId(){return this.id;}

    public void setCommenterId(int commenterId){this.commenterId=commenterId;}
    public int getCommenterId(){return this.commenterId;}

    public void setCommenter(String commenter){this.commenter=commenter;}
    public String getCommenter(){return this.commenter;}

    public void setLiteratureId(int literatureId){this.literatureId=literatureId;}
    public int getLiteratureId(){return this.literatureId;}

    public void setShortContent(String shortContent){this.shortContent=shortContent;}
    public String getShortContent(){return this.shortContent;}

    public void setScore(int score){this.score=score;}
    public int getScore(){return this.score;}

    public void setStatus(int status){this.status=status;}
    public int getStatus(){return this.status;}

    public void setCommentTime(String commentTime){
        this.commentTime=commentTime;
    }

    public String getCommentTime(){
        return this.commentTime;
    }

    public String getCommentTimeString(){
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(this.commentTime);
    }

    public void setLiteratureTitle(String literatureTitle) {
        this.literatureTitle = literatureTitle;
    }
    public String getLiteratureTitle(){
        return this.literatureTitle;
    }
}
