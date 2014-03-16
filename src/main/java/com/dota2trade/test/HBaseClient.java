package com.dota2trade.test;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by teireien on 14-3-11.
 */
public class HBaseClient {
    static Configuration conf=null;
    static final HTablePool tablePool;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
        tablePool = new HTablePool(conf, 10);
    }

    /*
     * 创建表
     *
     * @tableName 表名
     *
     * @family 列族列表
     */
    public static boolean createTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            return false;
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
            return true;
        }
    }

    public static void putData(String tableName,Put put) throws IOException{
        HTableInterface table = tablePool.getTable(tableName);// 获取表
        //System.out.println("Auto flush:" + table.isAutoFlush());
        table.put(put);
    }

    public static void putListData(String tableName,List<Put> puts) throws IOException {
        HTableInterface table = (HTable) tablePool.getTable(tableName);// 获取表
        try {
            table.put(puts);
        } catch (Exception e) {
            System.out.println("Error:" + e);
            table.flushCommits();
        }
    }

    public static Result getData(String tableName, String rowKey) throws IOException {
        HTableInterface table = tablePool.getTable(tableName);// 获取表
        Get get = new Get(Bytes.toBytes(rowKey));

        Result result = table.get(get);

        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
        }
        return result;
    }
}
