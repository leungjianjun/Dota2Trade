package com.dota2trade.model.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-26
 * Time: 下午2:18
 * 用于创建索引的类
 */
public class Indexr {
    private Analyzer analyzer = new ChineseAnalyzer();//StandardAnalyzer(Version.LUCENE_46);

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
    public void txt2Index(String fileName,int literatureid) throws Exception{
        //索引文件夹
        Directory directory= FSDirectory.open(new File(Util.PAPER_INDEX_DIR));

        IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);
        IndexWriter writer=new IndexWriter(directory, iwc);

        File file=new File(Util.TXT_DIR+fileName);
        Document document=Util.fileToDocument(file,literatureid);
        writer.addDocument(document);
        System.out.println("filename=="+document.get("filename"));
        writer.close();
    }

}
