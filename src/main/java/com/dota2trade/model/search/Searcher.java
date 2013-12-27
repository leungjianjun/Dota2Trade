package com.dota2trade.model.search;

import com.dota2trade.model.Literature;
import org.apache.lucene.search.IndexSearcher;

import java.util.List;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-25
 * Time: 下午9:51
 *
 * @version 1.0
 * 文献检索业务类
 */
public class Searcher {
    /**
     * 简单条件检索
     * 检索范围：文献标题、摘要、关键字
     * */
    public List<Literature> simpleSearch(String keyWord){
        return null;
    }

    /**
     * 复杂条件检索
     * */
    public List<Literature> complexSearch(int literatureTypeId,ComplexCondition complexCondition){
        return null;
    }

    /**
     * 只按照文献标签检索
     * */
    public List<Literature> tagSearch(int literatureTypeId,String tags){
        return null;
    }


    //IndexSearcher indexSearcher=new IndexSearcher();
}
