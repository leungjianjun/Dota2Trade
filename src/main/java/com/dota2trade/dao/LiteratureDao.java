package com.dota2trade.dao;

import com.dota2trade.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:30
 */
public interface LiteratureDao {
    /**创建新文献*/
    boolean createLiterature(Literature literature);
    /**修改已有文献*/
    boolean updateLiterature(Literature literature);
    /**添加文献基本信息*/
    boolean addLiteratureMeta(LiteratureMeta literatureMeta);
    /**添加文献附件信息*/
    boolean addAttachment(Attachment attachment);
    /**添加出版社信息*/
    int addPublisher(Publisher publisher);
    /**添加文献出版社对应关系信息*/
    boolean addLiteraturePublisher(int literatureid,int publisherid);
    /**添加文献的引用关系*/
    boolean addCiteRelationship(List<CiteRelationship> citeRelationshipList);
}
