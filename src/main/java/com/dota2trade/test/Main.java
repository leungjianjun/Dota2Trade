package com.dota2trade.test;

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by teireien on 14-3-11.
 */
public class Main {
    public static void main(String args[]) throws Exception {
        DataPool dp = new DataPool();
        HBaseClient client = new HBaseClient();
        //建立Hbase表
//        String[] literature = {"literature_base","literature_info","literature_attribute"};
//        client.createTable("literature",literature);

        //插入20000条纪录
//        for(int i=0;i<20000;i++){
//            int rowkey = i+1;
//            Put put1 = new Put(Bytes.toBytes("literature"+rowkey));
//            put1.add(Bytes.toBytes("literature_info"), Bytes.toBytes("title"), Bytes.toBytes(dp.getTitle()));
//            put1.add(Bytes.toBytes("literature_info"),Bytes.toBytes("author"),Bytes.toBytes(dp.getAuthor()));
//            put1.add(Bytes.toBytes("literature_info"),Bytes.toBytes("publisher"),Bytes.toBytes(dp.getPublisher()));
//            put1.add(Bytes.toBytes("literature_info"),Bytes.toBytes("publish_year"),Bytes.toBytes(dp.getPublishYear()));
//            client.putData("literature",put1);
//        }
//        System.out.println("数据存入成功！");

        //根据倒排索引进行文献检索

    }
}
