package com.dota2trade.dao;

import com.dota2trade.model.Attribute;

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
    int addLiteratureType(String name);
    boolean deleteLiteratureType(int id);

    /********************属性信息配置*******************/
    /**添加一个文献类别的一个新属性
     * 顺序是：先查看属性表是否有重名同类型的属性，有则不创建新的属性，没有则创建。
     * 然后向类型-属性关系对应表插入记录，插入的关系默认ismust为1
     * */
    boolean addLiteraturetypeAttribute(int literatureTypeId,Attribute attribute);
    /**改变类型-属性关系的ismust字段,使得结果与原来相反*/
    boolean changeAttributeIsmust(int literatureTypeId,int attributeId,int ismust);
    /**删除一条类型-属性*/
    boolean deleteLiteraturetypeAttribute(int literatureTypeId,Attribute attribute);
    /**获得一个文献类型的所有已配置属性*/
    List<Attribute> getAllAttributeOfLiteratureType(int literatureTypeId);
    /**获得一个文献类型的某一属性类型的已配置属性*/
    List<Attribute> getOneAttributeOfLiteratureType(int literatureTypeId,int type);

}