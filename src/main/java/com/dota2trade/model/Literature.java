package com.dota2trade.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:05
 *
 * 文献
 */
public class Literature {
    /**简单信息*/
    private int id;     //key
    private int creatorid;    //创建者id
    private int updaterid;    //修改者id
    private int status;    //是否保存为草稿 0:不是 1：是
    private int literaturetypeid;    //文献类别id

    /**通用基本信息*/
    private LiteratureMeta literatureMeta;
    /**出版社信息*/
    private Publisher publisher;
    /**附件信息*/
    private List<Attachment> attachmentList;
    /**引用关系*/
    private List<CiteRelationship> citeRelationshipList;

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLiteraturetypeid() {
        return literaturetypeid;
    }

    public void setLiteraturetypeid(int literaturetypeid) {
        this.literaturetypeid = literaturetypeid;
    }

    public LiteratureMeta getLiteratureMeta() {
        return literatureMeta;
    }

    public void setLiteratureMeta(LiteratureMeta literatureMeta) {
        this.literatureMeta = literatureMeta;
    }


    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<CiteRelationship> getCiteRelationshipList() {
        return citeRelationshipList;
    }

    public void setCiteRelationshipList(List<CiteRelationship> citeRelationshipList) {
        this.citeRelationshipList = citeRelationshipList;
    }
}