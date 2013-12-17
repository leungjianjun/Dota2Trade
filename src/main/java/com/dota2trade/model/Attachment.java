package com.dota2trade.model;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-16
 * Time: 上午11:49
 */
public class Attachment {
    private int id;
    private String name;
    private String link;
    private int creatorid;
    private int literatureid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public int getLiteratureid() {
        return literatureid;
    }

    public void setLiteratureid(int literatureid) {
        this.literatureid = literatureid;
    }
}
