package com.dota2trade.dao;

import com.dota2trade.model.Literature;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:30
 */
public interface LiteratureDao {
    boolean createLiterature(Literature literature,boolean isDraft);
    boolean updateLiterature(Literature literature,boolean isDraft);
}
