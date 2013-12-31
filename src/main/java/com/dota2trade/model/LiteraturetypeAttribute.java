package com.dota2trade.model;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-28
 * Time: 下午1:15
 *
 * @version 1.0
 */
public class LiteraturetypeAttribute {
    private int literaturetypeid;
    private Attribute attribute;
    private int ismust;

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public int getIsmust() {
        return ismust;
    }

    public void setIsmust(int ismust) {
        this.ismust = ismust;
    }

    public int getLiteraturetypeid() {

        return literaturetypeid;
    }

    public void setLiteraturetypeid(int literaturetypeid) {
        this.literaturetypeid = literaturetypeid;
    }
}
