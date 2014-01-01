package com.dota2trade.model;

import java.util.Date;

/**
 * Created by Cancy on 14-1-1.
 * 复杂评价某一属性值的model
 * 允许用户对同一文献发表多次评论，故加入时间属性
 */
public class CommentAttribute {
    private int id;
    private int literatureId;
    private int attributeId;
    private String attributeName;
    private String value;
    private int commenterId;
    private String commenter;
    private Date commentTime;
    private int status;

    public void setId(int id){this.id=id;}
    public int getId(){return this.id;}

    public void setLiteratureId(int literatureId){this.literatureId=literatureId;}
    public int getLiteratureId(){return this.literatureId;}

    public void  setAttributeId(int attributeId){this.attributeId=attributeId;}
    public int getAttributeId(){return this.attributeId;}

    public void setAttributeName(String attributeName){this.attributeName=attributeName;}
    public String getAttributeName(){return this.attributeName;}

    public void setValue(String value){this.value=value;}
    public String getValue(){return this.value;}

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
