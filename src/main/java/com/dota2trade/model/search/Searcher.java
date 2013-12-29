package com.dota2trade.model.search;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.dao.impl.LiteratureDaoImpl;
import com.dota2trade.model.Literature;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-25
 * Time: 下午9:51
 *
 * @version 1.0
 * 文献检索业务类
 */
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/servlet-context.xml"})
public class Searcher {

    private LiteratureDao literatureDao;
    @Autowired
    public void setLiteratureDao(LiteratureDao literatureDao) {
        this.literatureDao = literatureDao;
    }

    /**
     * 简单条件检索
     * 检索范围：文献标题、摘要、关键字
     * */
    public List<Literature> simpleSearch(String keyWord){
        List<Literature> literatureList=new ArrayList<Literature>();
        Directory dir;
        IndexReader reader= null;
        TopDocs topdocs= null;
        try {
            dir = FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
            reader = DirectoryReader.open(dir);

        IndexSearcher searcher=new IndexSearcher(reader);
        Term term=new Term("content", keyWord);
        Query query=new TermQuery(term);

        topdocs = searcher.search(query, 5);

        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
        for(int i=0; i < scoreDocs.length; i++){
            Document document=searcher.doc(scoreDocs[i].doc);
            int id=Integer.getInteger(document.get("id"));
            literatureList.add(literatureDao.getLiteratureById(id));
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return literatureList;
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
