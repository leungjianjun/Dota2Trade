package com.dota2trade.dao;

import com.dota2trade.model.Attribute;
import com.dota2trade.model.LiteratureType;
import com.dota2trade.model.LiteraturetypeAttribute;

import java.util.List;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-23
 * Time: 下午4:42
 *
 * @version 1.0
 */
public interface ConfigDao {
    /********************文献类别配置*******************/
    /**返回自增的id*/
    int addLiteratureType(LiteratureType literatureType);
    boolean deleteLiteratureType(int id);
    boolean deleteLiteratureType(String typename);
    List<LiteratureType> getAllLiteratureTypes();

    /********************属性信息配置*******************/
    /**
     * 属性信息即根据不同的文献类别对应的不同的属性，所有文献有通用的必填文献信息为
     * LiteratureMeta,除此之外，每种文献有自己的属性信息，每个单独的属性为Attribute,
     * 存储时按照单个属性来存
     * */
    /**
     * 添加一个文献类别的一个新属性
     * 顺序是：先查看属性表是否有重名同类型的属性，有则不创建新的属性，没有则创建。
     * 然后向类型-属性关系对应表插入记录，插入的关系默认ismust为1
     * */
    boolean addLiteraturetypeAttribute(int literatureTypeId,Attribute attribute);
    boolean addLiteraturetypeAttribute(String literatureTypeName,Attribute attribute);
    /**插入一条属性*/
    int addAttribute(Attribute attribute);
    /**删除属性**/
    boolean deleteAttribute(String name,int type);
    /**改变类型-属性关系的ismust字段,使得结果与原来相反*/
    boolean changeAttributeIsmust(int literatureTypeId,int attributeId,int ismust);
    /**删除一条类型-属性*/
    boolean deleteLiteraturetypeAttribute(int literatureTypeId,Attribute attribute);
    boolean deleteLiteraturetypeAttribute(String literatureTypeName,Attribute attribute);
    /**获得一个文献类型的所有已配置属性*/
    List<LiteraturetypeAttribute> getAllAttributeOfLiteratureType(int literatureTypeId);
    List<LiteraturetypeAttribute> getAllAttributeOfLiteratureType(String literatureTypeName);
    /**（目前该方法无意义会出错）获得一个文献类型的某一属性类型的已配置属性（该方法考虑的是一个文献类型拥有不同的基本、引用和评论，而现在考虑不同类型文献拥有相同的评论以及引用配置）*/
    List<LiteraturetypeAttribute> getOneAttributeOfLiteratureType(int literatureTypeId,int type);
    /**评论以及引用相关**/
    List<Attribute> getAllAttributeByType(int type);
}
