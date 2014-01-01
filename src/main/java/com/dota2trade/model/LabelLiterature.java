package com.dota2trade.model;

/**
 * Created by teireien on 14-1-1.
 * 文献的标签
 */
public class LabelLiterature {
    private int id;
    private int literatureid;
    private int labelid;
    private int userid;

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}
    public int getLiteratureid(){return this.literatureid;}
    public void setLiteratureid(int literatureid){this.literatureid = literatureid;}
    public int getLabelid(){return this.labelid;}
    public void setLabelid(int labelid){this.labelid = labelid;}
    public int getUserid(){return this.userid;}
    public void setUserid(int userid){this.userid = userid;}
}
