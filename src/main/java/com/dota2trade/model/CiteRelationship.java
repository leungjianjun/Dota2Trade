package com.dota2trade.model;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-16
 * Time: 下午2:18
 * 被引用关系
 * @version 1.0
 */
public class CiteRelationship {
    private int id;
    private int literatureid;//被引用文献id
    private int citedbyid;//引用文献id
    private int citedtypeid;//引用类型，1**开头的为引用 2**开头的为被引用

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLiteratureid() {
        return literatureid;
    }

    public void setLiteratureid(int literatureid) {
        this.literatureid = literatureid;
    }

    public int getCitedbyid() {
        return citedbyid;
    }

    public void setCitedbyid(int citedbyid) {
        this.citedbyid = citedbyid;
    }

    public int getCitedtypeid() {
        return citedtypeid;
    }

    public void setCitedtypeid(int citedtypeid) {
        this.citedtypeid = citedtypeid;
    }
}
