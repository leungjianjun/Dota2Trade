package com.dota2trade.model;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-23
 * Time: 下午8:26
 *
 * @version 1.0
 */
public class LiteratureAttribute {
    private int id;
    private int literatureid;
    private int attributeid;
    private String attributename;
    private String value;

    public String getAttributename() {
        return attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename;
    }

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

    public int getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(int attributeid) {
        this.attributeid = attributeid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
