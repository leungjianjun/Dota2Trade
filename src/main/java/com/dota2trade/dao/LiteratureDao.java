package com.dota2trade.dao;

import com.dota2trade.model.Attachment;
import com.dota2trade.model.Literature;
import com.dota2trade.model.LiteratureMeta;

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
}
