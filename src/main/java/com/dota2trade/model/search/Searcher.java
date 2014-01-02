package com.dota2trade.model.search;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.Literature;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
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
import java.util.Iterator;
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
    private Directory index_directory = null;
    private IndexReader ireader = null;
    private IndexSearcher isearcher = null;

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
            index_directory = FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
            // 搜索过程**********************************
            // 实例化搜索器
            ireader = IndexReader.open(index_directory);
            isearcher = new IndexSearcher(ireader);
            // 使用QueryParser查询分析器构造Query对象
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
        List<String> idList=new ArrayList<String>();
        boolean empty[]={true,true,true,true,true,true,true};
        try {
            // 实例化IKAnalyzer分词器
            analyzer = new IKAnalyzer();

            // 使用QueryParser查询分析器构造Query对象
            BooleanQuery booleanQuery=new BooleanQuery();
            String location=complexCondition.getKeywordsLocated();
            System.out.println("关键词检索范围："+location);
            QueryParser parser=null;
            if(location.equals("title")){
                index_directory=FSDirectory.open(new File(Util.DB_INDEX_DIR));
                ireader=IndexReader.open(index_directory);
                parser=new QueryParser(Version.LUCENE_34, Util.TITLE_INDEX_FIELD,analyzer);
            }else if(location.equals("paper")){
                index_directory=FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
                ireader=IndexReader.open(index_directory);
                parser=new QueryParser(Version.LUCENE_34, "CONTENT",analyzer);
            }else{
                System.out.println("未知的关键字位置！");
            }

            isearcher=new IndexSearcher(ireader);


                if(!complexCondition.getAllKeywordsHave().equals("")
                        &&!complexCondition.getAllKeywordsHave().equals(" ")){
                   //不是空串
                   Query query=parser.parse(complexCondition.getAllKeywordsHave());
                   booleanQuery.add(query, BooleanClause.Occur.MUST);
                   idList=this.getIDList(booleanQuery,isearcher);
                    empty[0]=false;
                }else{
                    empty[0]=true;
                }
                if(!complexCondition.getPhraseHave().equals("")
                        &&!complexCondition.getPhraseHave().equals(" ")){
                    //*不是空串
                    Query query=parser.parse(complexCondition.getPhraseHave());
                    booleanQuery.add(query, BooleanClause.Occur.MUST);
                    idList=Util.removeDuplicate(idList,this.getIDList(booleanQuery,isearcher));
                    empty[1]=false;
                }else{
                    empty[1]=true;
                }
                if(!complexCondition.getOneOrMoreKeywordHave().equals("")
                        &&!complexCondition.getOneOrMoreKeywordHave().equals(" ")){
                    //*不是空串
                    Query query=parser.parse(complexCondition.getOneOrMoreKeywordHave());
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                    idList=Util.removeDuplicate(idList,this.getIDList(booleanQuery,isearcher));
                    empty[2]=false;
                }else{
                    empty[2]=true;
                }
                if(!complexCondition.getNoKeywordHave().equals("")
                        &&!complexCondition.getNoKeywordHave().equals(" ")){
                    //*不是空串
                    Query query=parser.parse(complexCondition.getNoKeywordHave());
                    booleanQuery.add(query, BooleanClause.Occur.MUST);
                    idList=Util.filt(idList,this.getIDList(booleanQuery,isearcher));
                    empty[3]=false;
                }else{
                    empty[3]=true;
                }
                if(!complexCondition.getAuthor().equals("")
                        &&!complexCondition.getAuthor().equals(" ")){
                    //*不是空串
                    /**这里还需改*/
                    //QueryParser parser_temp=new QueryParser(Version.LUCENE_34, Util.AUTHOR_INDEX_FIELD,analyzer);
                    Query query=parser.parse(complexCondition.getAuthor());
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                    idList=Util.removeDuplicate(idList,this.getIDList(booleanQuery,isearcher));
                    empty[4]=false;
                }else{
                    empty[4]=true;
                }
                if(!complexCondition.getPublisher().equals("")
                        &&!complexCondition.getPublisher().equals(" ")){
                    //*不是空串
                    /**这里还需改*/
                    Query query=parser.parse(complexCondition.getPublisher());
                    booleanQuery.add(query, BooleanClause.Occur.MUST);
                    idList=Util.removeDuplicate(idList,this.getIDList(booleanQuery,isearcher));
                    empty[5]=false;
                }else{
                    empty[5]=true;
                } if(!complexCondition.getBegin().equals("")
                        &&!complexCondition.getBegin().equals(" ")){
                    /*/*//*不是空串
                    Query query=parser.parse(complexCondition.getBegin());
                    booleanQuery.add(query, BooleanClause.Occur.MUST);*/

                    idList=Util.mixfilt(idList,
                            Util.getStrList(
                                    literatureDao.publishedYearIdList(
                                            complexCondition.getBegin(),complexCondition.getEnd())));
                empty[6]=false;
                }else{
                empty[6]=true;
                }
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("complexSearch finally block.");
        }
        boolean tag=true;//全为空条件的标志
        for(int i=0;i<empty.length-1;i++){
            if(empty[i]==false){
                tag=false;
            }
        }
        if(tag==true){
            //前六个条件都为空
            if(empty[6]==false){
                //查询年份区间不为空
                idList=Util.getStrList(
                        literatureDao.publishedYearIdList(
                                complexCondition.getBegin(),complexCondition.getEnd()));
                for(Iterator iterator=idList.iterator();iterator.hasNext();){
                    String id_str=(String)iterator.next();
                    literatureList.add(literatureDao.getLiteratureById(Integer.valueOf(id_str)));
                }
                System.out.println("empty[6]==false:"+complexCondition.getBegin());
                return literatureList;
            }else{
                //是空条件
                System.out.println("空条件");
                return literatureDao.getAllLiterature();
            }
        }else{
            //空条件查询
            for(Iterator iterator=idList.iterator();iterator.hasNext();){
                String id_str=(String)iterator.next();
                literatureList.add(literatureDao.getLiteratureById(Integer.valueOf(id_str)));
            }
            System.out.println("falfalfalfal");
            return literatureList;
        }
    }

    /**
     * 匹配结果的ID list
     * */
    public List<String> getIDList(Query query,IndexSearcher isearcher){
        List<String> idList=new ArrayList<String>();
        try{
        // 搜索相似度最高的n条记录
        TopDocs topDocs = isearcher.search(query,Util.SEARCH_RESULT);
        System.out.println("命中：" + topDocs.totalHits);
        // 输出结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (int i = 0; i < topDocs.totalHits; i++) {
            Document targetDoc = isearcher.doc(scoreDocs[i].doc);
            idList.add(targetDoc.get("ID"));
        }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("木有异常！");
        }
        return idList;
    }
    /**
     * 只按照文献标签检索
     * */
    public List<Literature> tagSearch(int literatureTypeId,String tags){
        return null;
    }


    //IndexSearcher indexSearcher=new IndexSearcher();
}
