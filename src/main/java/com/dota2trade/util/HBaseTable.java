package com.dota2trade.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FilterList;

import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 13-9-17
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 */
public class HBaseTable {

    static Configuration cfg = HBaseConfiguration.create();
    static {
        cfg.set("hbase.zookeeper.quorum", "127.0.0.1");
        cfg.set("hbase.zookeeper.property.clientPort", "2181");
    }

    /**
     *
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        //create user table which has user_base and user_info column family
        deleteTable("user");
        String[] userColumn = {"user_base","user_info"};
        createTable("user",userColumn);
        //create literature table which has
        deleteTable("literature");
        String[] literature = {"literature_base","literature_info","literature_attribute"};
        createTable("literature",literature);

        //插入数据
        //RowKey可以使用uuid
        String uuid = UUID.randomUUID().toString();
        //插入和修改是一样的
        insert("user", uuid, "user_base","account",Bytes.toBytes("test2"));
        insert("user", UUID.randomUUID().toString(), "user_base","account",Bytes.toBytes("test3"));
        insert("user", UUID.randomUUID().toString(), "user_base","account",Bytes.toBytes("test4"));
        insert("user", UUID.randomUUID().toString(), "user_base","account",Bytes.toBytes("test5"));
        //修改数据

        getAllRecord("user");
        System.out.println(getTableCount("user"));

        find("user");
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public static boolean createTable(String tableName, String[] columnFamily) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(cfg);
        if (admin.tableExists(tableName)) {
            return false;
        }else{
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for(String column:columnFamily){
                tableDesc.addFamily(new HColumnDescriptor(column));
            }
            admin.createTable(tableDesc);
            return true;
        }
    }

    /**
     * Delete a table
     */
    public static boolean deleteTable(String tableName) throws IOException {
        try {
            HBaseAdmin admin = new HBaseAdmin(cfg);
            if (!admin.tableExists(tableName)) {
                return false;
            }else{
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                System.out.println("delete table " + tableName + " ok.");
                return true;
            }
        } catch (MasterNotRunningException e) {
            e.printStackTrace();

        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param value
     * @throws IOException
     */
    public static void insert(String tableName, String rowKey, String columnFamily, String qualifier, byte[] value) {
        try {
            HTable table = new HTable(cfg, tableName);
            Put p = new Put(Bytes.toBytes(rowKey));
            p.add(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), value);
            table.put(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String tableName, String rowKey, String columnFamily, String qualifier, String value){
        insert(tableName,rowKey,columnFamily,qualifier,Bytes.toBytes(value));
    }

    /**
     * 这个方法可以得到表的行数，用来作为插入数据的ID。这是一种极其shit的做法。
     * @param tableName
     * @return
     */
    public static int getTableCount(String tableName){
        int number = 0;
        try {
            HTable table = new HTable(cfg, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            for (Result rs = ss.next(); rs != null; rs = ss.next()) {
                number++;
            }
            return number;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void delete(String tableName, String rowKey) throws IOException {
        HTable table = new HTable(cfg, tableName);
        Delete d = new Delete(Bytes.toBytes(rowKey));
        table.delete(d);
    }

    /**
     * Get a row
     */
    public static void getOneRecord (String tableName, String rowKey) throws IOException{
        HTable table = new HTable(cfg, tableName);
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        for(KeyValue kv : rs.raw()){
            System.out.print(new String(kv.getRow()) + " " );
            System.out.print(new String(kv.getFamily()) + ":" );
            System.out.print(new String(kv.getQualifier()) + " " );
            System.out.print(kv.getTimestamp() + " " );
            System.out.println(new String(kv.getValue()));
        }
    }

    /**
     * Scan (or list) a table
     */
    public static void getAllRecord (String tableName) {
        try{
            HTable table = new HTable(cfg, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            for(Result r:ss){
                for(KeyValue kv : r.raw()){
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** SQL中select语句，但是HBase没有。所以查找要用filter方法。下面只是个参考例子而已，详细参考下面例子
     * 参考 https://hbase.apache.org/book/client.filter.html
     * @param tableName
     * @throws IOException
     */
    public static void find(String tableName) throws IOException {
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
                Bytes.toBytes("user_base"),Bytes.toBytes("account"),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("test2")
        );
        list.addFilter(filter1);
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                Bytes.toBytes("user_base"),Bytes.toBytes("account"),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("test4")
        );
        list.addFilter(filter2);

        HTable table = new HTable(cfg, tableName);
        Scan scan = new Scan();
        scan.setFilter(list);
        ResultScanner ss = table.getScanner(scan);

        for(Result r:ss){
            for(KeyValue kv : r.raw()){
                System.out.print(new String(kv.getRow()) + " ");
                System.out.print(new String(kv.getFamily()) + ":");
                System.out.print(new String(kv.getQualifier()) + " ");
                System.out.print(kv.getTimestamp() + " ");
                System.out.println(new String(kv.getValue()));
            }
        }
    }

}
