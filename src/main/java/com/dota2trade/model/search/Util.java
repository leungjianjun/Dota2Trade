package com.dota2trade.model.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;

import java.io.*;

/**
 * 通用方法类
 * */
public class Util {
	public static String PAPER_DIR="attachment/paper/";
	public static String TXT_DIR="index/temptxt/";
	public static String PAPER_INDEX_DIR="index/paperindex/";
	public static String DB_INDEX_DIR="index/dbindex/";
	/**
	 * 取得不带扩展名的文件名
	 * */
	public static String getFilenameWithnoExtension(String fileName){
		String name=fileName.substring(0,fileName.lastIndexOf("."));
		return name;
	}
	
	/** 
     * 判断是否是pdf文件 
     * @param fileName 
     * @return 
     */  
    public static boolean isPDFFile(String fileName) {  
        if(fileName.lastIndexOf(".pdf") > 0) {  
            return true;  
        }  
        return false;  
    }
    
    /** 
     * 判断是否是txt文件 
     * @param fileName 
     * @return 
     */  
    public static boolean isTxtFile(String fileName) {  
        if(fileName.lastIndexOf(".txt") > 0) {  
            return true;  
        }  
        return false;  
    }

    /**
     * 将文件转换成Document对象
     * @param file
     * @return
     * @throws Exception
     */
    public static Document fileToDocument(File file,int id) throws Exception {
        Document document=new Document();
        document.add(new TextField("id",String.valueOf(id), Field.Store.YES));
        document.add(new TextField("filename", file.getName(), Field.Store.YES));
        document.add(new TextField("content", getFileContent(file), Field.Store.YES));
        document.add(new LongField("size", file.getTotalSpace(), Field.Store.YES));
        return document;
    }
    /**
     * 读取文件内容
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileContent(File file) throws Exception{
        Reader reader = new InputStreamReader(new FileInputStream(file),"GBK");
        BufferedReader br = new BufferedReader(reader);
        String result ="";
        while(br.readLine() != null){
            result = result+"\n"+br.readLine();
        }
        br.close();
        reader.close();
        return result;
    }
}
