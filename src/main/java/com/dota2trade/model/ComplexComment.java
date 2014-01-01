package com.dota2trade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cancy on 14-1-1.
 */
public class ComplexComment {
    private int literatureId;
    private List<CommentAttribute> commentAttributes=new ArrayList<CommentAttribute>();
    private int commenterId;
    private String commenter;
    private Date commentTime;
    private int status;

    public void setLiteratureId(int literatureId){this.literatureId=literatureId;}
    public int getLiteratureId(){return this.literatureId;}

    public void setCommentAttributes(List<CommentAttribute> commentAttributes){this.commentAttributes=commentAttributes;}
    public List<CommentAttribute> getCommentAttributes(){return this.commentAttributes;}

    /**最好先初始化list之外的属性后，再添加commentAttribute的值，另外切记如果是草稿，需要将id先添加至commentAttribute**/
    public void addCommentAttribute(CommentAttribute commentAttribute){
        commentAttribute.setCommenterId(this.commenterId);
        commentAttribute.setCommentTime(this.commentTime);
        commentAttribute.setLiteratureId(this.literatureId);
        commentAttribute.setStatus(this.status);
        this.commentAttributes.add(commentAttribute);
    }
    public void removeCommentAttribute(){}

    public void setCommenterId(int commenterId){this.commenterId=commenterId;}
    public int getCommenterId(){return this.commenterId;}

    public void setCommenter(String commenter){this.commenter=commenter;}
    public String getCommenter(){return this.commenter;}

    public void setStatus(int status){this.status=status;}
    public int getStatus(){return this.status;}

    public void setCommentTime(Date commentTime){
        this.commentTime=commentTime;
    }

    public Date getCommentTime(){
        return this.commentTime;
    }

    public String getCommentTimeString(){
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(this.commentTime);
    }
}
