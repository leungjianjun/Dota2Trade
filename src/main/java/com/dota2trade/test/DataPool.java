package com.dota2trade.test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.File;
import java.io.BufferedReader;

/**
 * Created by teireien on 14-3-11.
 */
public class DataPool {
    private static final int DICTIONARY_LENGTH=2000;
    private String url="";
    private List dictionary;
    private List<String> publisher;

    public DataPool(){
        dictionary=readDictionary();
        publisher = readPublisher();
    }

    public List<String> readDictionary(){
        try{
            List<String> result=new ArrayList();
            String temp = null;
            File file = new File("dictionary.txt");
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(read);
            while((temp=reader.readLine())!=null &&!"".equals(temp)){
                temp = temp.split(" ")[1];
                result.add(temp);
            }
            read.close();
            return result;
        }
        catch (Exception e) {
            System.out.println("读取文件--->失败！- 原因：文件路径错误或者文件不存在");
            e.printStackTrace();
            return null;
        }
    }

    public List<String> readPublisher(){
        try{
            List result=new ArrayList();
            String temp = null;
            File file = new File("publisher.txt");
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(read);
            while((temp=reader.readLine())!=null &&!"".equals(temp)){
                result.add(temp);
            }
            read.close();
            return result;
        }
        catch (Exception e) {
            System.out.println("读取文件--->失败！- 原因：文件路径错误或者文件不存在");
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 设计等概率的算法来保证每个数字能够被选择到
     * 同时保证每一次选择重复的概率比较小
     *
     * @param limit  范围 0～limit
     * @return 一个随机数字
     */
    public int getRandom(int limit){
        Vector<Integer> v=new Vector<Integer>();
        Random random = new Random();
        int k =  random.nextInt(limit);
        if(v.contains(k)){
            int j = (Integer)v.get(0);
            if(j+1 <limit){
                v.removeAll(v);
                v.add(j + 1);
            }else{
                v.removeAll(v);
                v.add(j - 1);
            }
        }else{
            v.removeAll(v);
            v.add(k);
        }
        return v.get(0);
    }
    public HashSet<Integer> getRandoms(int times){
        HashSet<Integer> rSet = new HashSet<Integer>();
        Random random = new Random();
        int i = random.nextInt(times);
        i=i+2;
        try{
            for(int m=0;m<i;m++){
                int rad = getRandom(DICTIONARY_LENGTH);
                rSet.add(rad);
                Thread.sleep(10);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return rSet;
    }
    public String getTitle(){
        String result = "";
        HashSet<Integer> titleSet = getRandoms(8);
        Iterator<Integer> iterator=titleSet.iterator();
        if(iterator.hasNext()){
            result=result+dictionary.get(iterator.next());
        }
        while(iterator.hasNext()){
            result = result+" "+ dictionary.get(iterator.next());
        }
        return result;
    }
    public String getAuthor(){
        HashSet<Integer> authorSet = getRandoms(2);
        String result="";
        Iterator<Integer> iterator=authorSet.iterator();
        while(iterator.hasNext()){
            result = result + dictionary.get(iterator.next())+" ";
        }
        return result;
    }
    public String getPublisher(){
        Random random = new Random();
        int i = random.nextInt(50);
        return publisher.get(i);
    }
    public int getPublishYear(){
        Random random = new Random();
        return random.nextInt(115)+1900;
    }
}
//literatureid,title,author,publisher,publish_year