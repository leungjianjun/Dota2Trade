package com.dota2trade.model.search;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.Literature;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.wltea.analyzer.lucene.IKAnalyzer;

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
    private Analyzer analyzer;
    private Directory paper_index_directory = null;
    private Directory db_index_directory = null;
    private IndexReader paperireader = null;
    private IndexSearcher paperisearcher = null;
    private IndexReader dbireader = null;
    private IndexSearcher dbisearcher = null;

    private LiteratureDao literatureDao;
    @Autowired
    public void setLiteratureDao(LiteratureDao literatureDao) {
        this.literatureDao = literatureDao;
    }

    /**
     * 简单条件检索
     * 检索范围：论文内容（全部信息）
     * */
    public List<Literature> simpleSearch(String keyWord){
        List<Literature> literatureList=new ArrayList<Literature>();
        try {
            // 实例化IKAnalyzer分词器
            analyzer = new IKAnalyzer();
            // 索引文件夹
            paper_index_directory = FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
            // 搜索过程**********************************
            // 实例化搜索器
            paperireader = IndexReader.open(paper_index_directory);
            paperisearcher = new IndexSearcher(paperireader);
            // 使用QueryParser查询分析器构造Query对象
            QueryParser qp = new QueryParser(Version.LUCENE_34, "CONTENT",
                    analyzer);
            qp.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = qp.parse(keyWord);
            // 搜索相似度最高的n条记录
            TopDocs topDocs = paperisearcher.search(query,Util.SEARCH_RESULT);
            System.out.println("命中：" + topDocs.totalHits);
            // 输出结果
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (int i = 0; i < topDocs.totalHits; i++) {
                Document targetDoc = paperisearcher.doc(scoreDocs[i].doc);
                int id=Integer.valueOf(targetDoc.get("ID"));
                System.out.println("ID: "+id);
                literatureList.add(literatureDao.getLiteratureById(id));
                System.out.println("ID"+targetDoc.get("ID")+"匹配度"+scoreDocs[i].score+" 文件名：" + targetDoc.get("FILENAME"));
            }
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("simpleSearch finally block.");
        }

        return literatureList;
    }

    /**
     * 复杂条件检索
     * */
    public List<Literature> complexSearch(ComplexCondition complexCondition){
        List<Literature> literatureList=new ArrayList<Literature>();
        try {
            // 实例化IKAnalyzer分词器
            analyzer = new IKAnalyzer();
            // paper索引文件夹
            paper_index_directory = FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
            // DB索引文件夹
            db_index_directory = FSDirectory.open(new File(Util.DB_INDEX_DIR));
            // 搜索过程**********************************
            // 实例化搜索器
            paperireader = IndexReader.open(paper_index_directory);
            paperisearcher = new IndexSearcher(paperireader);
            // 实例化搜索器
            dbireader = IndexReader.open(db_index_directory);
            dbisearcher = new IndexSearcher(dbireader);
            // 使用QueryParser查询分析器构造Query对象
            BooleanQuery booleanQuery=new BooleanQuery();
            String location=complexCondition.getKeywordsLocated();
            if(location.equals("title")){
                QueryParser title_qp = new QueryParser(Version.LUCENE_34, Util.TITLE_INDEX_FIELD,analyzer);
                if(!complexCondition.getAllKeywordsHave().equals("")){
                   //不是空串
                   Query title_query=title_qp.parse(complexCondition.getAllKeywordsHave());
                   booleanQuery.add(title_query, BooleanClause.Occur.MUST);
                }else{
                   Query title_query=title_qp.parse("test");
                   booleanQuery.add(title_query, BooleanClause.Occur.MUST);
                }
                /*if(!complexCondition.getPhraseHave().equals("")){
                    *//*//*不是空串
                    Query title_query=title_qp.parse(complexCondition.getAllKeywordsHave());
                    booleanQuery.add(title_query, BooleanClause.Occur.MUST);*//*
                }
                if(!complexCondition.getOneOrMoreKeywordHave().equals("")){

                }
                if(!complexCondition.getNoKeywordHave().equals("")){

                }*/
                // 搜索相似度最高的n条记录
                TopDocs topDocs = dbisearcher.search(booleanQuery,Util.SEARCH_RESULT);
                System.out.println("命中：" + topDocs.totalHits);
                // 输出结果
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (int i = 0; i < topDocs.totalHits; i++) {
                    Document targetDoc = dbisearcher.doc(scoreDocs[i].doc);
                    int id=Integer.valueOf(targetDoc.get("ID"));
                    System.out.println("ID: "+id);
                    literatureList.add(literatureDao.getLiteratureById(id));
                    System.out.println("ID"+targetDoc.get("ID")+"匹配度"+scoreDocs[i].score+" 文件名：" + targetDoc.get("FILENAME"));
                }

            }else if(location.equals("paper")){
                QueryParser paper_qp = new QueryParser(Version.LUCENE_34, "CONTENT",analyzer);
                if(!complexCondition.getAllKeywordsHave().equals("")){
                    //不是空串
                    Query title_query=paper_qp.parse(complexCondition.getAllKeywordsHave());
                    booleanQuery.add(title_query, BooleanClause.Occur.MUST);
                }else{
                    Query title_query=paper_qp.parse("test");
                    booleanQuery.add(title_query, BooleanClause.Occur.MUST);
                }
                // 搜索相似度最高的n条记录
                TopDocs topDocs = paperisearcher.search(booleanQuery,Util.SEARCH_RESULT);
                System.out.println("命中：" + topDocs.totalHits);
                // 输出结果
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (int i = 0; i < topDocs.totalHits; i++) {
                    Document targetDoc = paperisearcher.doc(scoreDocs[i].doc);
                    int id=Integer.valueOf(targetDoc.get("ID"));
                    System.out.println("ID: "+id);
                    literatureList.add(literatureDao.getLiteratureById(id));
                    System.out.println("ID"+targetDoc.get("ID")+"匹配度"+scoreDocs[i].score+" 文件名：" + targetDoc.get("FILENAME"));
                }
            }else{
                System.out.println("未知的关键词查询位置！");
            }

            /*QueryParser author_qp = new QueryParser(Version.LUCENE_34, Util.AUTHOR_INDEX_FIELD,analyzer);

            QueryParser qp = new QueryParser(Version.LUCENE_34, "CONTENT",
                    analyzer);
            qp.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = qp.parse(keyWord);
            // 搜索相似度最高的n条记录
            TopDocs topDocs = isearcher.search(query,Util.SEARCH_RESULT);
            System.out.println("命中：" + topDocs.totalHits);
            // 输出结果
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++) {
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                int id=Integer.valueOf(targetDoc.get("ID"));
                System.out.println("ID: "+id);
                literatureList.add(literatureDao.getLiteratureById(id));
                System.out.println("ID"+targetDoc.get("ID")+"匹配度"+scoreDocs[i].score+" 文件名：" + targetDoc.get("FILENAME"));
            }*/
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("simpleSearch finally block.");
        }

        return literatureList;
    }

    /**
     * 只按照文献标签检索
     * */
    public List<Literature> tagSearch(int literatureTypeId,String tags){
        return null;
    }


    //IndexSearcher indexSearcher=new IndexSearcher();
}
