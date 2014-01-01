package com.dota2trade.model.search;

import java.io.*;

/**
 * 通用方法类
 * */
public class Util {
	public static String PAPER_DIR="attachment/paper/";
	public static String TXT_DIR="index/temptxt/";
	public static String PAPER_INDEX_DIR="index/paperindex/";
	public static String DB_INDEX_DIR="index/dbindex/";

    public static int SEARCH_RESULT=10;//查询结果数

    public static String TITLE_INDEX_FIELD="TITLE";
    public static String ABSTRACT_INDEX_FIELD="ABSTRACT";
    public static String AUTHOR_INDEX_FIELD="AUTHOR";
    public static String PUBLISHED_YEAR_INDEX_FIELD="PUBLISHED_YEAR";
    public static String KEY_WORDS_INDEX_FIELD="KEY_WORDS";

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
     * 读取文件内容
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileContent(File file) throws Exception{
        Reader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
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
