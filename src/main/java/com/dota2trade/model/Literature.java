package com.dota2trade.model;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:05
 *
 * 文献
 */
public class Literature {
    private int id;
    private int creatorid;
    private int updaterid;
    private String status;
    private int literaturetypeid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public int getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(int updaterid) {
        this.updaterid = updaterid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLiteraturetypeid() {
        return literaturetypeid;
    }

    public void setLiteraturetypeid(int literaturetypeid) {
        this.literaturetypeid = literaturetypeid;
    }
}