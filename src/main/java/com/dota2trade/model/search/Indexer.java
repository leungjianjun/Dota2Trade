package com.dota2trade.model.search;

import com.dota2trade.model.LiteratureMeta;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-26
 * Time: 下午2:18
 * 用于创建索引的类
 */
public class Indexer {
    private Analyzer analyzer;
    private Directory directory = null;
    private IndexWriter iwriter = null;

    public Indexer(){}
    /**
     * 对论文进行索引
     * @param fileName 论文名称，带有.pdf后缀名
     * @param literatureid 文献id
     * */
    public void indexPaper(String fileName,int literatureid){

        if(Util.isPDFFile(fileName)==true){
            /*第一步：pdf转txt*/
            try {
                PDFReader.pdf2Txt(fileName);
                /*第二步：txt做索引*/
                this.txt2Index(Util.getFilenameWithnoExtension(fileName)+".txt",literatureid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("NOT PDF FILE ERROR!");
        }
    }
    /**
     * txt文件索引
     * @param fileName txt文件名，有.txt后缀名的
     * */
    public void txt2Index(String fileName,int fileId) throws Exception{
        try {
            // 实例化IKAnalyzer分词器
            analyzer = new IKAnalyzer();
            //读取文件内容
            String fileContent = Util.getFileContent(new File(Util.TXT_DIR+fileName));
            System.out.println("文件内容："+fileContent);
            // 索引文件夹
            directory = FSDirectory.open(new File(Util.PAPER_INDEX_DIR));
            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(
                    Version.LUCENE_34, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            iwriter = new IndexWriter(directory, iwConfig);
            // 写入索引
            Document doc = new Document();
            doc.add(new Field("ID", Integer.toString(fileId), Field.Store.YES,
                    Field.Index.NOT_ANALYZED));// ID不做索引
            doc.add(new Field("FILENAME", fileName, Field.Store.YES,
                    Field.Index.NOT_ANALYZED));// 文件名不做索引
            doc.add(new Field("CONTENT", fileContent, Field.Store.YES,
                    Field.Index.ANALYZED));// 文件内容做索引
            iwriter.addDocument(doc);
            iwriter.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally block");
        }
    }
    /**
     * 对文献的元信息进行索引
     * */
    public void indexDB(LiteratureMeta literatureMeta){
        try {
            // 实例化IKAnalyzer分词器
            analyzer = new IKAnalyzer();

            // 索引文件夹
            directory = FSDirectory.open(new File(Util.DB_INDEX_DIR));
            // 配置IndexWriterConfig
            IndexWriterConfig iwConfig = new IndexWriterConfig(
                    Version.LUCENE_34, analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            iwriter = new IndexWriter(directory, iwConfig);
            // 写入索引
            Document doc = new Document();
            doc.add(new Field("ID", Integer.toString(literatureMeta.getLiteratureid()), Field.Store.YES,
                    Field.Index.NOT_ANALYZED));// ID不做索引
            doc.add(new Field(Util.TITLE_INDEX_FIELD, literatureMeta.getTitle(), Field.Store.YES,
                    Field.Index.ANALYZED));// 做索引
            doc.add(new Field(Util.ABSTRACT_INDEX_FIELD, literatureMeta.getLiterature_abstract(), Field.Store.YES,
                    Field.Index.ANALYZED));// 做索引
            doc.add(new Field(Util.AUTHOR_INDEX_FIELD, literatureMeta.getAuthor(), Field.Store.YES,
                    Field.Index.ANALYZED));// 做索引
            doc.add(new Field(Util.PUBLISHED_YEAR_INDEX_FIELD, literatureMeta.getPublished_year(), Field.Store.YES,
                    Field.Index.ANALYZED));// 做索引
            doc.add(new Field(Util.KEY_WORDS_INDEX_FIELD, literatureMeta.getKey_words(), Field.Store.YES,
                    Field.Index.ANALYZED));// 做索引

            iwriter.addDocument(doc);
            iwriter.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally block");
        }
    }
}
