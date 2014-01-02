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
    /*******************add methods***********************/
    /**
     * 创建新文献
     * @return 文献id
     * */
    int createLiterature(Literature literature);
    /**添加文献基本信息*/
    boolean addLiteratureMeta(LiteratureMeta literatureMeta);
    /**添加文献附件信息*/
    boolean addAttachment(List<Attachment> attachmentList);
    /**添加出版社信息*/
    int addPublisher(Publisher publisher);
    /**添加文献出版社对应关系信息*/
    boolean addLiteraturePublisher(int literatureid,int publisherid);
    /**添加文献的引用关系*/
    boolean addCiteRelationship(List<CiteRelationship> citeRelationshipList);


    /**添加不同类型的文献特有的属性信息*/
    boolean addLiteratureAttribute(List<LiteratureAttribute> literatureAttributeList);

    /*********************delete methods*********************************/
    /**删除文献*/
    boolean deleteLiterature(int literatureid);
    /**删除文献基本信息*/
    boolean deleteLiteratureMeta(int literatureid);
    /**删除文献所有附件信息*/
    boolean deleteAttachmentByLiteratureId(int literatureid);
    /**删除一条附件信息*/
    boolean deleteAttachmentByAttachmentId(int attachmentid);
    /**出版社信息永久保存，不删除*/
    //int deletePublisher(int publisherid);
    /**删除文献出版社对应关系信息*/
    boolean deleteLiteraturePublisher(int literatureid);
    /**删除一条引用关系*/
    boolean deleteCiteRelationshipById(int citeRelationshipid);
    /**删除一个文献的所有引用关系*/
    boolean deleteACiteRelationshipByLiteratureId(int literatureid);


    /*********************update methods*********************************/
    /**更新文献属性信息*/
    boolean updateLiterature(Literature literature);
    /**更新文献基本信息*/
    boolean updateLiteratureMeta(LiteratureMeta literatureMeta);
    /**更新出版社信息*/
    int updatePublisher(Publisher publisher);
    /**更新文献出版社对应关系信息*/
    boolean updateLiteraturePublisher(int literatureid,int publisherid);
    /**更新一条附件信息,主要是修改已有附件的名称*/
    boolean updateAttachment(Attachment attachment);
    /**更新文献引用关系，主要是在修改已有的引用关系的情况下更新引用类型*/
    boolean updateCiteRelationship(List<CiteRelationship> citeRelationshipList);
    /**更新文献的某个特殊属性的值*/
    boolean updateLiteratureAttribute(LiteratureAttribute literatureAttribute);

    /*********************get methods************************************/
    /**获取一个文献*/
    Literature getLiteratureById(int literatureid);
    /**根据userid获取到该用户上传的所有文献的基本信息*/
    List<LiteratureMeta> getAllLiteratureMetaByUserid(int userid);
    /**获取一个文献的基本信息*/
    LiteratureMeta getLiteratureMetaByLiteratureId(int literatureid);
    /**获取一个文献的出版社信息*/
    Publisher getPublisherByLiteratureId(int literatureid);
    /**获取一个文献的所有附件*/
    List<Attachment> getAllAttachmentByLiteratureId(int literatureid);
    /**获取一个文献的所有引用关系*/
    List<CiteRelationship> getAllCiteRelationshipByLiteratureId(int literatureid);
    /**获取所有引用一个文献的文献*/
    List<CiteRelationship> getAllCiteRelationshipByCitedById(int citedbyid);
    /**获取组内所有文献的基本信息*/
    List<LiteratureMeta> getAllLiteratureMeta();
    /**获取组内所有文献的基本信息*/
    List<Literature> getAllLiterature();

    /**获取一个文献的所有特有属性信息*/
    List<LiteratureAttribute> getLiteratureAttribute(int literatureid);

    List<Integer> publishedYearIdList(String begin,String end);
}
