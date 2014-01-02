package com.dota2trade.model.search;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /**
     * 两个数组的去重合并
     * */
    public static List<String> removeDuplicate(List<String> list1,List<String> list2){
        //去重
        for(Iterator iterator1=list1.iterator();iterator1.hasNext();){
            String str1=(String)iterator1.next();
            for(Iterator iterator2=list2.iterator();iterator2.hasNext();){
                String str2=(String)iterator2.next();
                if(str1.equals(str2)){
                    iterator2.remove();
                }
            }
        }
        //合并
        for(Iterator iterator2=list2.iterator();iterator2.hasNext();){
            list1.add((String)iterator2.next());
        }
        return list1;
    }

    /**
     * 两个数组筛选
     * 过滤掉第一个数组中在第二个数组中出现的元素
     * */
    public static List<String> filt(List<String> list1,List<String> list2){
        //去重
        for(Iterator iterator1=list1.iterator();iterator1.hasNext();){
            String str1=(String)iterator1.next();
            for(Iterator iterator2=list2.iterator();iterator2.hasNext();){
                String str2=(String)iterator2.next();
                if(str1.equals(str2)){
                    iterator1.remove();
                }
            }
        }

        return list1;
    }
    /**
     * 取两个数组的交集
     * */
    public static List<String> mixfilt(List<String> list1,List<String> list2){
        List<String> list=new ArrayList<String>();
        //去重
        for(Iterator iterator1=list1.iterator();iterator1.hasNext();){
            String str1=(String)iterator1.next();
            for(Iterator iterator2=list2.iterator();iterator2.hasNext();){
                String str2=(String)iterator2.next();
                if(str1.equals(str2)){
                    list.add(str1);
                }
            }
        }

        return list;
    }

    public static List<String> getStrList(List<Integer> integerList){
        List<String> list=new ArrayList<String>();
        for(Iterator iterator=integerList.iterator();iterator.hasNext();){
            Integer integer=(Integer)iterator.next();
            String str=integer.toString();
            list.add(str);
        }
        return list;
    }
}
